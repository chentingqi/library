// youluSvnWar.groovy

def call(map) {

pipeline {
    agent {
            label "${map.node}"
    }
    options {
        //保持构建的最大个数
        buildDiscarder(logRotator(numToKeepStr: '5')) 
    }
    environment {
        examples_var1 = sh(script: 'echo "当前的时间是: `date`"', returnStdout: true).trim()
    }
    
    parameters {
    choice(
        description: '选择部署环境',
        name: 'ENV',
        choices: ['dev','test','uat','prod','rollback']
    )
    string(name: 'APP_VERSION', defaultValue: "${map.APP_VERSION}",description: 'API测试地址') 
    string(name: 'SVN_BRANCH_VERSION', defaultValue: "${map.SVN_BRANCH_VERSION}",description: 'API测试地址') 
    string(name: 'PROJECT_VERSION', defaultValue: "${map.PROJECT_VERSION}",description: 'API测试地址') 
     
    }
    stages{
        stage("拉取代码") {
            when { anyOf { environment name: 'ENV', value: 'dev' } }
            steps {
                sh "echo code pull"
                checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', 
                excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, 
                ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, 
                credentialsId: "${map.svn_cert}", depthOption: 'infinity', ignoreExternalsOption: true, local: '.', 
                remote: "${map.svn_url}"]], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
                
            }
        }

        stage('代码扫描'){
            when { anyOf { environment name: 'ENV', value: 'dev' } }
            steps {
             withSonarQubeEnv('sonar') {
             sh "echo SONAR启动 "
             sh "cp /data/build-devops/sonar-project.properties $workspace"
             sh "sed -i 's/jobname/${JOB_NAME}/g' sonar-project.properties"
             sh "/data/sonar-scanner-3.0.0.702-linux/bin/sonar-scanner -X -Dsonar.host.url=http://172.16.106.88:9000"
            }
            script {
                timeout(time: 1, unit: "HOURS") {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {

                            error "未通过Sonarqube的代码质量阈检查，请及时修改！failure: ${qg.status}"
                }}}
                
            }
        }
        stage('代码构建'){
            when { anyOf { environment name: 'ENV', value: 'dev' } }
            steps {
            sh "echo ${map.MAVEN_BUILD_COMMAND}"
            sh "${map.MAVEN_BUILD_COMMAND}"
        }
        }
        
        stage('单元测试'){
            when { anyOf { environment name: 'ENV', value: 'dev' } }
            steps {
            sh "mvn test"
            
        }
        }
        stage('拉取制品'){
            when { 
                anyOf { environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback' 
                      } 
                }
            steps {
            checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: 'renshuaipeng', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: "http://171.15.16.189:11080/svn/youlu/MicroService/${PROJECT_VERSION}/BaseData"]], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
            }
        }
        stage('部署服务'){
            when { 
                anyOf { environment name: 'ENV', value: 'dev' ; 
                        environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback' 
                      } 
                }
            steps {
            script {
            
            if (params.ENV == "dev") {
               
               for (item in map.DEV_IP.tokenize(',')){
                   echo "deploy ${ENV}" 
                   echo "deploy ${item}"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}"
                   sh "salt ${item} cmd.run '${map.DEPLOY_COMMAND}'"
                   
                }
            }
            if (params.ENV == "test") {
               for (item in map.TEST_IP.tokenize(',')){
                   echo "deploy ${ENV}" 
                   echo "deploy ${item}"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}"
                   sh "salt ${item} cmd.run '${map.DEPLOY_COMMAND}'"
                   
                }
            }
            if (params.ENV == "uat") {
               
               for (item in map.UAT_IP.tokenize(',')){
                   echo "deploy ${ENV}" 
                   echo "deploy ${item}"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}"
                   sh "salt ${item} cmd.run '${map.DEPLOY_COMMAND}'"
                   
                }
            }
            if (params.ENV == "prod") {
               for (item in map.PROD_IP.tokenize(',')){
                   echo "deploy ${ENV}" 
                   echo "deploy ${item}"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}"
                   sh "salt ${item} cmd.run '${map.DEPLOY_COMMAND}'"
                  
                }
            }
            if (params.ENV == "rollback") {
               for (item in map.PROD_IP.tokenize(',')){
                   echo "deploy ${ENV}" 
                   echo "deploy ${item}"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}"
                   sh "salt ${item} cmd.run '${map.DEPLOY_COMMAND}'"
                   
                }
            }
        }
            }
        }
        stage('日志输出'){
            when { 
                anyOf { environment name: 'ENV', value: 'dev' ; 
                        environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback'
                      } 
                }
            steps {
            script {
            if (params.ENV == "dev") {
               sh "sleep 5s"
               for (item in map.DEV_IP.tokenize(',')){
                   echo "${item}：获取服务最后100行日志"
                   sh "salt ${item} cmd.run 'tail -n100 ${map.APP_LOG}'"
               }
            }
            if (params.ENV == "test") {
               sh "sleep 5s"
               for (item in map.TEST_IP.tokenize(',')){
                   echo "${item}：获取服务最后100行日志"
                   sh "salt ${item} cmd.run 'tail -n100 ${map.APP_LOG}'"
               }
            }
            if (params.ENV == "uat") {
               sh "sleep 5s"
               for (item in map.TEST_IP.tokenize(',')){
                   echo "${item}：获取服务最后100行日志"
                   sh "salt ${item} cmd.run 'tail -n100 ${map.APP_LOG}'"
               }
            }
            if (params.ENV == "prod") {
               sh "sleep 5s"
               for (item in map.TEST_IP.tokenize(',')){
                   echo "${item}：获取服务最后100行日志"
                   sh "salt ${item} cmd.run 'tail -n100 ${map.APP_LOG}'"
               }
            }
            if (params.ENV == "rollback") {
               sh "sleep 5s"
               for (item in map.TEST_IP.tokenize(',')){
                   echo "${item}：获取服务最后100行日志"
                   sh "salt ${item} cmd.run 'tail -n100 ${map.APP_LOG}'"
               }
            }
            }
        }
        }
        stage('服务检查'){
            when { 
                anyOf { environment name: 'ENV', value: 'dev' ; 
                        environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback'
                      } 
                }
            steps {
            script {
            if (params.ENV == "dev") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh /data/jenkins_home/workspace/${JOB_NAME}/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
               for (item in map.DEV_IP.tokenize(',')){
                   echo "${item}：查看服务进程是否存在"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${item} cmd.run 'sh ${DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
               }
            }
            if (params.ENV == "test") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh /data/jenkins_home/workspace/${JOB_NAME}/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
               for (item in map.TEST_IP.tokenize(',')){
                   echo "${item}：查看服务进程是否存在"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${item} cmd.run 'sh ${DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
               }
            }
            if (params.ENV == "uat") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh /data/jenkins_home/workspace/${JOB_NAME}/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
               for (item in map.UAT_IP.tokenize(',')){
                   echo "${item}：查看服务进程是否存在"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${item} cmd.run 'sh ${DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
               }
            }
            if (params.ENV == "prod") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh /data/jenkins_home/workspace/${JOB_NAME}/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
               for (item in map.PROD_IP.tokenize(',')){
                   echo "${item}：查看服务进程是否存在"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${item} cmd.run 'sh ${DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
               }
            }
            if (params.ENV == "rollback") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh /data/jenkins_home/workspace/${JOB_NAME}/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
               for (item in map.PROD_IP.tokenize(',')){
                   echo "${item}：查看服务进程是否存在"
                   sh "salt ${item} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${item} cmd.run 'sh ${DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
               }
            }
            }
            }
        }
        stage('API测试'){
            steps{
            sh "echo 进行API自动化测试"
            //sh "rm -rf *"
            //git branch: 'master', credentialsId: "gitlab_rsp", url: 'https://git.youlu.com/test-samples/user-center-api-test.git'
            //sh "/data/apache-maven-3.6.3/bin/mvn clean test"
            }
        }
    }
    post{
        always{
            echo "构建结束"
        }
        success {
          echo '恭喜您，构建成功！！！'
			
		  emailext attachLog: true, attachmentsPattern: 'target/cucumber.html', 
          subject: "'${ENV}环境：${env.JOB_NAME} [${env.BUILD_NUMBER}]' 构建成功",
		  body: """
          <div id="content">
          <h1>CI报告</h1>
          <div id="sum2">
	      <h2>构建结果 - <font color="#00FF00">SUCCESS</font></h2>
          <ul>
          <li>项目名称 : <a>${env.JOB_NAME}</a></li>
          <li>项目视图 : <a href="http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline">http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline</a></li>
	      <li>项目 URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
	      <li>构建编号 : 第${BUILD_NUMBER}次构建</li>
          <li>构建日志 ：<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
	      <li>代码扫描 ：<a href="http://192.168.15.178:9000/dashboard?id=${JOB_NAME}">http://192.168.15.178:9000/dashboard?id=${JOB_NAME}</a></li>
          </ul>
          </div>
          <div id="sum0">
          <h2>制品信息</h2>
          <ul>
          <li>SVN项目的地址 : <a>${SVNURL}</a></li>
	      <li>制品库地址 : <a href="http://192.168.10.82:8081">http://192.168.10.82:8081</a></li>
	      <li>本次生成制品 ：${map.WAR}</li>
	      <li>本次提测版本 : ${NEXUS_REP}.${NEXUS_PATCH}</li>
          <li>测试制品库下载链接 ：<a href="http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war">http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war</a></li>
	      <li>正式制品库下载链接 ：<a href="http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war">http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war</a></li>
          </ul>
          </div>
          <div id="sum0">
          <h2>运行环境</h2>
          <ul>
	      <li>项目发布的环境    : ${ENV}</li>
	      <li>项目环境访问地址  : ${map.DEPLOY_DIR} </li>
	      <li>项目运行环境的IP  : Dev: ${map.DEV_IP}   TEST: ${map.TEST_IP}  PROD: ${map.PROD_IP}</li>
          </ul>
          </div>
          </div>""",mimeType: 'text/html',to : "${map.EMAILLIST}",from: "cjt@youlu.com"
		  }
        failure {
            echo '抱歉，构建失败！！！'
			
		  emailext attachLog: true, attachmentsPattern: 'target/cucumber.html', 
          subject: "'${ENV}环境：${env.JOB_NAME} [${env.BUILD_NUMBER}]' 构建失败",
		  body: """
          <div id="content">
          <h1>CI报告</h1>
          <div id="sum2">
	      <h2>构建结果 - <font color="#FF0000">FAILED</font></h2>
          <ul>
          <li>项目名称 : <a>${env.JOB_NAME}</a></li>
          <li>项目视图 : <a href="http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline">http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline</a></li>
	      <li>项目 URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
	      <li>构建编号 : 第${BUILD_NUMBER}次构建</li>
          <li>构建日志 ：<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
	      <li>代码扫描 ：<a href="http://192.168.15.178:9000/dashboard?id=${JOB_NAME}">http://192.168.15.178:9000/dashboard?id=${JOB_NAME}</a></li>
          </ul>
          </ul>
          </div>
          <div id="sum0">
          <h2>制品信息</h2>
          <ul>
          <li>SVN项目的地址 : <a>${SVNURL}</a></li>
	      <li>制品库地址 : <a href="http://192.168.10.82:8081">http://192.168.10.82:8081</a></li>
	      <li>本次生成制品 ：${map.WAR}</li>
	      <li>本次提测版本 : ${NEXUS_REP}.${NEXUS_PATCH}</li>
	      <li>测试制品库下载链接 ：<a href="http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war">http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war</a></li>
	      <li>正式制品库下载链接 ：<a href="http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war">http://192.168.10.82:8081/repository/${NEXUS_REP}/${map.NEXUS_GROUP}/${NEXUS_PATCH}/${map.NEXUS_NAME1}/${map.NEXUS_NAME2}/${map.NEXUS_NAME}.war</a></li>
          </ul>
          </div>
          </div>""", mimeType: 'text/html',to : "${map.EMAILLIST}",from: "cjt@youlu.com"
        }
        unstable {
            echo '该任务已经被标记为不稳定任务....'
        }
        changed {
            echo ''
        }
    }

}

}
