def call(map) {

pipeline {
    agent { node {  label "${map.node}"   //指定运行节点的标签或者名称
            }
    }

    options {
        timestamps()  //日志会有时间
        skipDefaultCheckout()  //删除隐式checkout scm语句
        disableConcurrentBuilds() //禁止并行
        timeout(time: 1, unit: 'HOURS')  //流水线超时设置1h
    }
    parameters {
    choice(
        description: '选择部署环境',
        name: 'ENV',
        choices: ['test','uat','prod','rollback']
    )
    choice(
      description: "V3.*X*是X迭代提测版本根目录,迭代从0起,例如 V3.0",
      name: 'NEXUS_REP',
      choices: ['youlu','V3.27','V3.28','V3.29','V3.30','V1.06','V1.07','V1.08','V1.09','V1.10','V1.11']
     )
     
     choice(
      description: "V3.*X*.*Y*是X迭代上线前研发多次提测版本，Y表示第几次提测，例如 V3.0.1表示V3的0迭代第1次提交;      P00*Z*是X迭代上线后研发多次提测补丁，Z表示第几次补丁， P001 表示V3的0迭代第1次补丁;         上线前提测从1起，上线后提测从1起",
      name: 'NEXUS_PATCH',
      choices: ['','001','002','003','004','005','006','007','008','009','010','011','012','013','014','015','016','017','P001','P002','P003','P004','P005','P006']
     )

    }


    stages {
        //下载代码
        stage("GetCode"){ //阶段名称
            steps{  //步骤
                timeout(time:5, unit:"MINUTES"){   //步骤超时时间
                    script{ //填写运行代码
                        print('获取代码')
                        tools.PrintMes("获取代码",'green')
                        codePull.gitPull("${map.giturl}","${map.gitbranch}","${map.gitcert}")
                    }
                
                }
            }
        }
        
        stage("CodeScan"){
                    steps{
                        timeout(time:30, unit:"MINUTES"){
                            script{
                                print("代码扫描")
                                tools.PrintMes("代码扫描",'green')
                                codeScan.codeScan("java","$workspace","${JOB_NAME}")
                            }
                        }
                    }
                }
        stage("Build"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('应用打包')
                                  tools.PrintMes("应用打包",'green')
                                  codeBuild.codeBuild("${map.jdkversion}","${map.buildType}","$workspace","${map.buildShell}")
                            }
                        }
                    }
                }
        stage("deploy"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{ 
                                  print('服务部署')
                                  tools.PrintMes("服务部署",'green')
                                  if (params.ENV == "test") {
                                      for (item in map.TEST_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${map.JAR}","${item}","${JOB_NAME}","${map.DEPLOY_DIR}","${map.DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "uat") {
                                      for (item in map.UAT_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${map.JAR}","${item}","${JOB_NAME}","${map.DEPLOY_DIR}","${map.DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "prod") {
                                      for (item in map.PROD_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${map.DEPLOY_DIR}","${map.DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "roleback") {
                                      for (item in map.PROD_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${map.JAR}","${item}","${JOB_NAME}","${map.DEPLOY_DIR}","${map.DEPLOY_COMMAND}",)
                   
                                       }
                                  }

                            }
                        }
                    }
        }
        stage("log_check"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('日志检查')
                                  tools.PrintMes("日志检查",'green')
                                   if (params.ENV == "test") {
                                      for (item in map.TEST_IP.tokenize(',')){
                                          echo "回显节点${item}服务最后100行日志" 
                                          logCheck.logCheck("salt","${item}","${JOB_NAME}","${map.APP_LOG}")
                   
                                       }
                                  }
                                  
                            }
                        }
                    }
        }
        stage("service_check"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('服务检查')
                                  tools.PrintMes("服务检查",'green')
                                  if (params.ENV == "test") {
                                      for (item in map.TEST_IP.tokenize(',')){
                                          echo "检查节点服务状态" 
                                          serviceCheck.serviceCheck("salt","${item}","${JOB_NAME}","${map.SERVICE_NAME}")
                   
                                       }
                                  }
                            }
                        }
                    }
        }
        stage("nexus_push"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('制品上传')
                                  tools.PrintMes("制品上传",'green')
                                  if (params.ENV == 'test'){
                                  nexusPush.nexusPush("jar","${map.JAR}","${NEXUS_REP}","${map.NEXUS_GROUP}","${NEXUS_PATCH}","${map.NEXUS_NAME1}","${map.NEXUS_NAME2}")
                                  }
                            }
                        }
                    }
        }
                
        
    }

    //构建后操作
    post {
        always {
            script{
                println("always")
            }
        }

        success {
            script{
                currentBuild.description = "\n 构建成功!" 
            }
        }

        failure {
            script{
                currentBuild.description = "\n 构建失败!" 
            }
        }

        aborted {
            script{
                currentBuild.description = "\n 构建取消!" 
            }
        }
    }
}
}