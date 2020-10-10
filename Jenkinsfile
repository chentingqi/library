#!groovy

@Library('library-test') _

def tools = new org.devops.tools()
def codePull = new org.devops.codePull()
def codeScan = new org.devops.codeScan()
def unitTest = new org.devops.unitTest()
def codeBuild = new org.devops.codeBuild()
def changeSlb = new org.devops.changeSlb()
def deployService = new org.devops.deployService()
def logCheck = new org.devops.logCheck()
def serviceCheck = new org.devops.serviceCheck()
def nexusPush = new org.devops.nexusPush()
//def mailSend = new org.devops.mailSend()

//String workspace = "/data/jenkins_home/workspace"

//Pipeline
pipeline {
    agent { node {  label "master"   //指定运行节点的标签或者名称
            }
    }

    options {
        timestamps()  //日志会有时间
        skipDefaultCheckout()  //删除隐式checkout scm语句
        disableConcurrentBuilds() //禁止并行
        buildDiscarder(logRotator(numToKeepStr: '5')) 
        timeout(time: 1, unit: 'HOURS')  //流水线超时设置1h
    }
    environment {
        giturl = "https://git.youlu.com/yunwei-devops/sonar-ali.git"
        gitbranch = "master"
        gitcert = "chenjingtao-git"
        jdkversion = "jdk8"
        buildType = "maven"
        buildShell = "/data/apache-maven-3.6.3/bin/mvn clean package -Dmaven.test.skip=true"
        DEV_IP = "192.168.10.83"
        TEST_IP = "192.168.10.83"
        UAT_IP = "192.168.10.83"
        PROD_IP = "192.168.10.83"
        DEPLOY_DIR = "/data/"
        DEPLOY_COMMAND = "/data/deploy_service.sh"
        JAR = "target/sonar-pmd-plugin-2.6.jar"
        APP_LOG = "/data/app.log"
        SERVICE_NAME = "salt"
        NEXUS_GROUP = "devops"
        NEXUS_SEVICE = "sonar-pmd-plugin"
        NEXUS_NAME1 = "sonar-pmd-plugin"
        NEXUS_NAME2 = "2.6"
        NEXUS_NAME = "sonar-pmd-plugin"
        BOOK = "changelist.txt"
        EMAILLIST = "cjt@youlu.com"

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
                        echo "origin：${giturl}   branch: ${gitbranch},  凭据：${gitcert}"
                        codePull.gitPull("${giturl}","${gitbranch}","${gitcert}")
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
        stage("unitTest"){
                    steps{
                        timeout(time:30, unit:"MINUTES"){
                            script{
                                print("单元测试")
                                tools.PrintMes("单元测试",'green')
                                unitTest.unitTest()
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
                                  codeBuild.codeBuild("${jdkversion}","${buildType}","$workspace","${buildShell}")
                            }
                        }
                    }
                }
        stage("umount 59"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('卸载节点')
                                  tools.PrintMes("卸载节点172.16.106.59",'green')
                                  changeSlb.umountSlb("172.16.106.59")
                                  sleep 30
                            }
                        }
                    }
                }
        stage("deploy59"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{ 
                                  print('服务部署')
                                  tools.PrintMes("服务部署",'green')
                                  if (params.ENV == "test") {
                                      for (item in TEST_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "uat") {
                                      for (item in UAT_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "prod") {
                                      for (item in PROD_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "roleback") {
                                      for (item in PROD_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
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
                                      for (item in TEST_IP.tokenize(',')){
                                          echo "回显节点${item}服务最后100行日志" 
                                          logCheck.logCheck("salt","${item}","${JOB_NAME}","${APP_LOG}")
                   
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
                                      for (item in TEST_IP.tokenize(',')){
                                          echo "检查节点服务状态" 
                                          serviceCheck.serviceCheck("salt","${item}","${JOB_NAME}","${SERVICE_NAME}")
                   
                                       }
                                  }
                            }
                        }
                    }
        }
        stage("mount 59"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('恢复节点')
                                  tools.PrintMes("恢复节点172.16.106.59",'green')
                                  changeSlb.mountSlb("${172.16.106.59}")
                            }
                        }
                    }
                }
        stage("umount 65"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('卸载节点')
                                  tools.PrintMes("卸载节点172.16.106.65",'green')
                                  changeSlb.umountSlb("172.16.106.65")
                                  sleep 30
                            }
                        }
                    }
                }
        stage("deploy65"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{ 
                                  print('服务部署')
                                  tools.PrintMes("服务部署",'green')
                                  if (params.ENV == "test") {
                                      for (item in TEST_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "uat") {
                                      for (item in UAT_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "prod") {
                                      for (item in PROD_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
                                       }
                                  }
                                  if (params.ENV == "roleback") {
                                      for (item in PROD_IP.tokenize(',')){
                                          echo "deploy ${ENV}" 
                                          echo "deploy ${item}"
                                          deployService.deployService("salt","${JAR}","${item}","${JOB_NAME}","${DEPLOY_DIR}","${DEPLOY_COMMAND}",)
                   
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
                                      for (item in TEST_IP.tokenize(',')){
                                          echo "回显节点${item}服务最后100行日志" 
                                          logCheck.logCheck("salt","${item}","${JOB_NAME}","${APP_LOG}")
                   
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
                                      for (item in TEST_IP.tokenize(',')){
                                          echo "检查节点服务状态" 
                                          serviceCheck.serviceCheck("salt","${item}","${JOB_NAME}","${SERVICE_NAME}")
                   
                                       }
                                  }
                            }
                        }
                    }
        }
        stage("mount 65"){
                    steps{
                        timeout(time:20, unit:"MINUTES"){
                            script{
                                  print('恢复节点')
                                  tools.PrintMes("恢复节点172.16.106.65",'green')
                                  changeSlb.mountSlb("${172.16.106.65}")
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
                                  nexusPush.nexusTime("jar","${JAR}","${NEXUS_REP}","${NEXUS_GROUP}","${NEXUS_PATCH}","${NEXUS_NAME1}","${NEXUS_NAME2}","${BOOK}")
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
            emailext body: '''
        <!DOCTYPE html>
        <html>
        <head>
        <meta charset="UTF-8">
        <title>${ENV, var="JOB_NAME"}-第${BUILD_NUMBER}次构建日志</title>
        </head>
 
        <body leftmargin="8" marginwidth="0" topmargin="8" marginheight="4"
        offset="0">
        <table width="95%" cellpadding="0" cellspacing="0"
        style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif">
        <tr>
            <td>(本邮件是程序自动下发的，请勿回复！)</td>
        </tr>
        <tr>
            <td><h2>
                    <font color="#00FF00">构建结果 - ${BUILD_STATUS}</font>
                </h2></td>
        </tr>
        <tr>
            <td><br />
            <b><font color="#0B610B">构建信息</font></b>
            <hr size="2" width="100%" align="center" /></td>
        </tr>
        <tr>
            <td>
                <ul>
                    <li>项目名称&nbsp;：&nbsp;${PROJECT_NAME}</li>
                    <li>构建编号&nbsp;：&nbsp;第${BUILD_NUMBER}次构建</li>
                    <li>GIT&nbsp;&nbsp;Url&nbsp;：&nbsp;<a href="${GITURL}">${GITURL}</a></li>
                    <li>GIT&nbsp;分支：&nbsp;${GITBRANCH}</li>
                    <li>触发原因：&nbsp;${CAUSE}</li>
                    <li>构建日志：&nbsp;<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
                    <li>构建&nbsp;&nbsp;Url&nbsp;：&nbsp;<a href="${BUILD_URL}">${BUILD_URL}</a></li>
                    <li>工作目录&nbsp;：&nbsp;<a href="${PROJECT_URL}ws">${PROJECT_URL}ws</a></li>
                    <li>项目&nbsp;&nbsp;Url&nbsp;：&nbsp;<a href="${PROJECT_URL}">${PROJECT_URL}</a></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td><br />
            <b><font color="#0B610B">部署信息</font></b>
            <hr size="2" width="100%" align="center" /></td>
        </tr>
        <tr>
            <td>
                <ul>
                    <li>部署环境&nbsp;：&nbsp;${ENV}</li>
                    <li>制品版本&nbsp;：&nbsp;${NEXUS_VERSION}.${NEXUS_PATCH}</li>
                    <li>代码扫描地址：&nbsp;<a href="http://jenkins-check.niceloo.com:8082/dashboard?id=${JOB_NAME}">http://jenkins-check.niceloo.com:8082/dashboard?id=${JOB_NAME}</a></li>
                    <li>制品库地址：&nbsp;<a href="http://jenkins-check.niceloo.com:8081/">http://jenkins-check.niceloo.com:8081/</a></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td><b><font color="#0B610B">Changes Since Last
                        Successful Build:</font></b>
            <hr size="2" width="100%" align="center" /></td>
        </tr>
        <tr>
            <td>
                <ul>
                    <li>历史变更记录 : <a href="${PROJECT_URL}changes">${PROJECT_URL}changes</a></li>
                </ul> ${CHANGES_SINCE_LAST_SUCCESS,reverse=true, format="Changes for Build #%n:<br />%c<br />",showPaths=true,changesFormat="<pre>[%a]<br />%m</pre>",pathFormat="&nbsp;&nbsp;&nbsp;&nbsp;%p"}
            </td>
        </tr>
        <tr>
            <td><b>Failed Test Results</b>
            <hr size="2" width="100%" align="center" /></td>
        </tr>
        <tr>
            <td><pre
                    style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif">$FAILED_TESTS</pre>
                <br /></td>
        </tr>
        <tr>
            <td><b><font color="#0B610B">构建日志 (最后 100行):</font></b>
            <hr size="2" width="100%" align="center" /></td>
        </tr>
        <!-- <tr>
            <td>Test Logs (if test has ran): <a
                href="${PROJECT_URL}ws/TestResult/archive_logs/Log-Build-${BUILD_NUMBER}.zip">${PROJECT_URL}/ws/TestResult/archive_logs/Log-Build-${BUILD_NUMBER}.zip</a>
                <br />
            <br />
            </td>
        </tr> -->
        <tr>
            <td><textarea cols="80" rows="30" readonly="readonly"
                    style="font-family: Courier New">${BUILD_LOG, maxLines=100}</textarea>
            </td>
        </tr>
    </table>
</body>
</html>


    ''', subject: "'${ENV}环境：${env.JOB_NAME} [${env.BUILD_NUMBER}]' 构建成功", to: "${EMAILLIST}", from: 'cjt@youlu.com'

            
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