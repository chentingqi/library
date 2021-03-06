// youluSvnWar.groovy

def call(map) {

pipeline {
    agent {
            label "${map.node}"
    }
    options {
        timestamps()  //日志会有时间
        skipDefaultCheckout()  //删除隐式checkout scm语句
        disableConcurrentBuilds() //禁止并行
        buildDiscarder(logRotator(numToKeepStr: '5')) 
        timeout(time: 1, unit: 'HOURS')  //流水线超时设置1h

    }
    environment {
        examples_var1 = sh(script: 'echo "当前的时间是: `date`"', returnStdout: true).trim()
    }
    
    parameters {
    choice(
        description: '选择部署环境',
        name: 'ENV',
        choices: ['test','stable','uat','prod','rollback-test','rollback-uat','rollback-prod']
    )
    string(name: 'APP_VERSION', defaultValue: "${map.APP_VERSION}",description: '构建成功后target目录下的版本号（test）')
    string(name: 'SVN_BRANCH_VERSION', defaultValue: "${map.SVN_BRANCH_VERSION}",description: 'SVN代码分支（test）')
    choice(
        description: '上线版本号（stable,uat,prod,rollback）',
        name: 'PROJECT_VERSION',
        choices: ['','3.1.79','3.1.80','3.1.81','3.1.82',,'3.1.83','3.1.84','3.1.85','3.1.86','3.1.87','3.1.88','3.1.89','3.1.90','3.1.91','3.1.92','3.1.93','3.1.94','3.1.95','3.1.96','3.1.97','3.1.98','3.1.99','test111','test222']
    )
    choice(
        description: '版本补丁（stable,uat,prod,rollback）',
        name: 'PROJECT_PATCH',
        choices: ['','001','002','hotfix01']
    )
    string(name: 'API_HOST', defaultValue: "${map.API_HOST}",description: '接口自动化测试')
    }
    stages{
        stage("拉取代码") {
            when { anyOf { environment name: 'ENV', value: 'test' } }
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
            when { anyOf { environment name: 'ENV', value: 'test' } }
            steps {
             withSonarQubeEnv('sonar') {
             sh "echo SONAR启动 "
             sh "cp /data/build-devops/sonar-project.properties $workspace"
             sh "sed -i 's/jobname/${JOB_NAME}/g' sonar-project.properties"
             sh "sed -i 's#workspace#$workspace#g' sonar-project.properties"
             //sh "/data/sonar-scanner-3.0.0.702-linux/bin/sonar-scanner -X -Dsonar.host.url=http://172.16.106.88:9000"
            }
            //script {
            //    timeout(time: 1, unit: "HOURS") {
            //    def qg = waitForQualityGate()
            //    if (qg.status != 'OK') {

            //                error "未通过Sonarqube的代码质量阈检查，请及时修改！failure: ${qg.status}"
            //    }}}
                
            }
        }
        stage('代码构建'){
            when { anyOf { environment name: 'ENV', value: 'test' } }
            steps {
            sh "echo ${map.MAVEN_BUILD_COMMAND}"
            sh "${map.MAVEN_BUILD_COMMAND}"
        }
        }
        
        stage('单元测试'){
            when { anyOf { environment name: 'ENV', value: 'dev' } }
            steps {
            sh "/data/maven/apache-maven-3.6.2/bin/mvn test -Dmaven.repo.local=/data/.repository"
            
        }
        }
        stage('拉取制品'){
            when { 
                anyOf { environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback-test' ; 
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: 'chenjingtao-svn', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: "http://171.15.16.189:11080/svn/youlu/MicroService/${PROJECT_VERSION}/${map.NEXUS_NAME}/${PROJECT_PATCH}"]], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
            }
        }
        stage('SLB卸载服务器节点1'){
            when { anyOf { environment name: 'ENV', value: 'uat' ;
                           environment name: 'ENV', value: 'prod' ; 
                           environment name: 'ENV', value: 'rollback-uat' ; 
                           environment name: 'ENV', value: 'rollback-prod' 
            } }
            steps {
                  sh "${map.EDIT_GATEWAT1}"
            
        }
        }
        stage('部署节点1'){
            when { 
                anyOf { environment name: 'ENV', value: 'dev' ; 
                        environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback-test' ; 
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            script {
            
            if (params.ENV == "dev") {
                   echo "deploy ${ENV} ${map.DEV_IP1}" 
                   sh "salt ${map.DEV_IP1} cp.get_file salt://${JOB_NAME}/${map.WAR} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.DEV_IP1} cmd.run '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
                   echo "deploy ${ENV} ${map.TEST_IP1}" 
                   sh "salt ${map.TEST_IP1} cp.get_file salt://${JOB_NAME}/${map.WAR} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.TEST_IP1} cmd.run '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
                   echo "deploy ${ENV} ${map.UAT_IP1}" 
                   sh "salt ${map.UAT_IP1} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.UAT_IP1} cmd.run '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
                   echo "deploy ${ENV} ${map.PROD_IP1}" 
                   sh "salt ${map.PROD_IP1} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.PROD_IP1} cmd.run '${map.DEPLOY_COMMAND}'"
            }
        }
            }
        }
        stage('日志输出1'){
            when { 
                anyOf { environment name: 'ENV', value: 'dev' ; 
                        environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback-test' ; 
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            script {
            if (params.ENV == "dev") {
               sh "sleep 5s"
                   echo "${map.DEV_IP1}：获取服务最后100行日志"
                   sh "salt ${map.DEV_IP1} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
               sh "sleep 5s"
                   echo "${map.TEST_IP1}：获取服务最后100行日志"
                   sh "salt ${map.TEST_IP1} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
               sh "sleep 5s"
               echo "${map.UAT_IP1}：获取服务最后100行日志"
               sh "salt ${map.UAT_IP1} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
               sh "sleep 5s"
               echo "${map.PROD_IP1}：获取服务最后100行日志"
               sh "salt ${map.PROD_IP1} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            }
        }
        }
        stage('服务检查1'){
            when { 
                anyOf { environment name: 'ENV', value: 'dev' ; 
                        environment name: 'ENV', value: 'test' ; 
                        environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback-test' ; 
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            script {
            if (params.ENV == "dev") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.DEV_IP1}：查看服务进程是否存在"
                   sh "salt ${map.DEV_IP1} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.DEV_IP1} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.TEST_IP1}：查看服务进程是否存在"
                   sh "salt ${map.TEST_IP1} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.TEST_IP1} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.UAT_IP1}：查看服务进程是否存在"
                   sh "salt ${map.UAT_IP1} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.UAT_IP1} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.PROD_IP1}：查看服务进程是否存在"
                   sh "salt ${map.PROD_IP1} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.PROD_IP1} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            }
            }
        }
        stage('网关改配节点2'){
            when { anyOf { environment name: 'ENV', value: 'uat' ;
                           environment name: 'ENV', value: 'prod' ; 
                           environment name: 'ENV', value: 'rollback-uat' ; 
                           environment name: 'ENV', value: 'rollback-prod' 
                           } }
            steps {
                  sh "${map.EDIT_GATEWAT2}"
            }
        }
        stage('部署节点2'){
            when { 
                anyOf { environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ;  
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            script {
            
            if (params.ENV == "dev") {
                   echo "deploy ${ENV} ${map.DEV_IP2}" 
                   sh "salt ${map.DEV_IP2} cp.get_file salt://${JOB_NAME}/${map.WAR} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.DEV_IP2} cmd.run '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
                   echo "deploy ${ENV} ${map.TEST_IP2}" 
                   sh "salt ${map.TEST_IP2} cp.get_file salt://${JOB_NAME}/${map.WAR} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.TEST_IP2} cmd.run '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
                   echo "deploy ${ENV} ${map.UAT_IP2}" 
                   sh "salt ${map.UAT_IP2} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.UAT_IP2} cmd.run '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
                   echo "deploy ${ENV} ${map.PROD_IP2}" 
                   sh "salt ${map.PROD_IP2} cp.get_file salt://${JOB_NAME}/${map.WAR_NAME} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                   sh "salt ${map.PROD_IP2} cmd.run '${map.DEPLOY_COMMAND}'"
            }
        }
            }
        }
        stage('日志输出2'){
            when { 
                anyOf { environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ;  
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            script {
            if (params.ENV == "dev") {
               sh "sleep 5s"
                   echo "${map.DEV_IP2}：获取服务最后100行日志"
                   sh "salt ${map.DEV_IP2} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
               sh "sleep 5s"
                   echo "${map.TEST_IP2}：获取服务最后100行日志"
                   sh "salt ${map.TEST_IP2} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
               sh "sleep 5s"
               echo "${map.UAT_IP2}：获取服务最后100行日志"
               sh "salt ${map.UAT_IP2} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
               sh "sleep 5s"
               echo "${map.PROD_IP2}：获取服务最后100行日志"
               sh "salt ${map.PROD_IP2} cmd.run 'tail -n100 ${map.APP_LOG}'"
            }
            }
        }
        }
        stage('服务检查2'){
            when { 
                anyOf { environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            script {
            if (params.ENV == "dev") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.DEV_IP2}：查看服务进程是否存在"
                   sh "salt ${map.DEV_IP2} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.DEV_IP2} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.TEST_IP2}：查看服务进程是否存在"
                   sh "salt ${map.TEST_IP2} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.TEST_IP2} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.UAT_IP2}：查看服务进程是否存在"
                   sh "salt ${map.UAT_IP2} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.UAT_IP2} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
               sh "sleep 5s"
               sh "cp /data/build-devops/service_check.sh $workspace/"
               sh "sed -i 's/jarname/${map.SERVICE_NAME}/g'  service_check.sh"
                   echo "${map.PROD_IP2}：查看服务进程是否存在"
                   sh "salt ${map.PROD_IP2} cp.get_file salt://${JOB_NAME}/service_check.sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh"
                   sh "salt ${map.PROD_IP2} cmd.run 'sh ${map.DEPLOY_DIR}/service_check-${JOB_NAME}.sh'"
            }
            }
            }
        }
        stage('网关改配双节点'){
            when { anyOf { environment name: 'ENV', value: 'uat' ;
                           environment name: 'ENV', value: 'prod' ; 
                           environment name: 'ENV', value: 'rollback-uat' ; 
                           environment name: 'ENV', value: 'rollback-prod'  } }
            steps {
                  sh "${map.EDIT_GATEWAT_ALL}"
            }
        }
        stage('上传SVN制品库'){
            when { 
                anyOf { environment name: 'ENV', value: 'stable' 
                      } 
            }
            steps{
            sh "cp /data/build-devops/svn/svn-upload.sh $workspace"
            sh "sed -i 's/app_version/${PROJECT_VERSION}/g' svn-upload.sh"
            sh "sed -i 's/app_patch/${PROJECT_PATCH}/g' svn-upload.sh"
            sh "sed -i 's/nexus_name/${map.NEXUS_NAME}/g' svn-upload.sh"
            sh "sed -i 's#app_package#${map.WAR}#g' svn-upload.sh"
            sh "sh svn-upload.sh"
            }
            }

        stage('API测试'){
            when { anyOf { environment name: 'ENV', value: 'test' ;
                           environment name: 'ENV', value: 'uat' ;
                           environment name: 'ENV', value: 'prod' ; 
                           environment name: 'ENV', value: 'rollback-test' ;
                           environment name: 'ENV', value: 'rollback-uat' ;
                           environment name: 'ENV', value: 'rollback-prod' 
            } }
            steps{
            sh "echo 进行API自动化测试"
            sh "rm -rf api-test"
            sh "cp /data/build-devops/api-test.sh $workspace"
            sh "sed -i 's#git_url#${map.TEST_GIT}#g' api-test.sh"
            //git branch: 'master', credentialsId: "chenjingtao-git", url: "${map.TEST_GIT}"
            sh "sh api-test.sh"
            }
        }
    }
    //构建后操作
    post {
        always {
            script{
                println("always")
            }
            emailext attachmentsPattern: 'api-test/target/cucumber.html', body: '''
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
                    <li>触发原因：&nbsp;${CAUSE}</li>
                    <li>流水线Url&nbsp;：&nbsp;<a href="${JENKINS_URL}blue/organizations/jenkins/${JOB_NAME}/detail/${JOB_NAME}/${BUILD_NUMBER}/pipeline">${JENKINS_URL}blue/organizations/jenkins/${JOB_NAME}/detail/${JOB_NAME}/${BUILD_NUMBER}/pipeline</a></li>
                    <li>构建&nbsp;&nbsp;Url&nbsp;：&nbsp;<a href="${BUILD_URL}">${BUILD_URL}</a></li>
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
                    <li>代码版本&nbsp;：&nbsp;${SVN_BRANCH_VERSION}</li>
                    <li>制品版本&nbsp;：&nbsp;${PROJECT_VERSION}.${PROJECT_PATCH}</li>
                    <li>代码扫描地址：&nbsp;<a href="http://192.168.10.83:8081/dashboard?id=${JOB_NAME}">http://192.168.10.83:8081/dashboard?id=${JOB_NAME}</a></li>
                    <li>制品库地址：&nbsp;<a href="http://192.168.11.247/svn/youlu/MicroService/${DEPLOY_VERSION}/${map.NEXUS_NAME}/${DEPLOY_PATCH}">http://192.168.11.247/svn/youlu/MicroService/${PROJECT_VERSION}/${map.NEXUS_NAME}/${PROJECT_PATCH}</a></li>
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


    ''', subject: "'${ENV}环境：${env.JOB_NAME} [${env.BUILD_NUMBER}]' 构建完成", to: "${map.EMAILLIST}", from: 'chenjingtao@jiaoyu361.com'

            
        }

        success {
            script{
                currentBuild.description = "\n ${ENV}-${PROJECT_VERSION}-${PROJECT_PATCH}构建成功!" 
            }
        }

        failure {
            script{
                currentBuild.description = "\n ${ENV}-${PROJECT_VERSION}-${PROJECT_PATCH}构建失败!" 
            }
        }

        aborted {
            script{
                currentBuild.description = "\n ${ENV}-${PROJECT_VERSION}-${PROJECT_PATCH}构建取消!" 
            }
        }
    }
}
}

