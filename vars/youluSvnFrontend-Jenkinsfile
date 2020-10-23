// youyidianFrontend.groovy

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
        choices: ['uat','prod','rollback-uat','rollback-prod']
    )
    string(name: 'APP_VERSION', defaultValue: "${map.APP_VERSION}",description: '构建成功后target目录下的版本号（test）')
    string(name: 'SVN_BRANCH_VERSION', defaultValue: "${map.SVN_BRANCH_VERSION}",description: 'SVN代码分支（test）')
    choice(
        description: '上线版本号（stable,uat,prod,rollback）',
        name: 'PROJECT_VERSION',
        choices: ['','3.1.62','3.1.79','3.1.80','3.1.81','3.1.82',,'3.1.83','3.1.84','3.1.85','3.1.86','3.1.87','3.1.88','3.1.89','3.1.90','3.1.91','3.1.92','3.1.93','3.1.94','3.1.95','3.1.96','3.1.97','3.1.98','3.1.99','test111','test222']
    )
    choice(
        description: '版本补丁（stable,uat,prod,rollback）',
        name: 'PROJECT_PATCH',
        choices: ['','001','002','hotfix01']
    )
    }
    stages{
        stage('拉取前端制品'){
            when { 
                anyOf { environment name: 'ENV', value: 'uat' ; 
                        environment name: 'ENV', value: 'prod' ; 
                        environment name: 'ENV', value: 'rollback-test' ; 
                        environment name: 'ENV', value: 'rollback-uat' ; 
                        environment name: 'ENV', value: 'rollback-prod' 
                      } 
                }
            steps {
            checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: 'chenjingtao-svn', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: "http://171.15.16.189:11080/svn/youlu/MicroService/${PROJECT_VERSION}/${map.NEXUS_NAME}/${ENV}/${PROJECT_PATCH}"]], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
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
                   sh "scp ${map.ZIP} msyoulu@${map.DEV_IP1}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.DEV_IP1} '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
                   echo "deploy ${ENV} ${map.TEST_IP1}" 
                   sh "scp ${map.ZIP} msyoulu@${map.TEST_IP1}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.TEST_IP1} '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
                   echo "deploy ${ENV} ${map.UAT_IP1}" 
                   sh "scp ${map.ZIP_NAME} msyoulu@${map.UAT_IP1}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.UAT_IP1} '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
                   echo "deploy ${ENV} ${map.PROD_IP1}" 
                   sh "scp ${map.ZIP_NAME} msyoulu@${map.PROD_IP1}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.PROD_IP1} '${map.DEPLOY_COMMAND}'"
            }
        }
            }
        }
        stage('部署节点2'){
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
                   echo "deploy ${ENV} ${map.DEV_IP2}" 
                   sh "scp ${map.ZIP} msyoulu@${map.DEV_IP2}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.DEV_IP2} '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "test" || params.ENV == "rollback-test") {
                   echo "deploy ${ENV} ${map.TEST_IP2}" 
                   sh "scp ${map.ZIP} msyoulu@${map.TEST_IP2}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.TEST_IP2} '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "uat" || params.ENV == "rollback-uat") {
                   echo "deploy ${ENV} ${map.UAT_IP2}" 
                   sh "scp ${map.ZIP_NAME} msyoulu@${map.UAT_IP2}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.UAT_IP2} '${map.DEPLOY_COMMAND}'"
            }
            if (params.ENV == "prod" || params.ENV == "rollback-prod") {
                   echo "deploy ${ENV} ${map.PROD_IP2}" 
                   sh "scp ${map.ZIP_NAME} msyoulu@${map.PROD_IP2}:${map.DEPLOY_DIR}"
                   sh "ssh msyoulu@${map.PROD_IP2} '${map.DEPLOY_COMMAND}'"
            }
        }
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
            sh "sed -i 's#app_package#${map.ZIP}#g' svn-upload.sh"
            sh "sh svn-upload.sh"
            }
            }

    }
    //构建后操作
    post {
        always {
            script{
                sh "echo '${examples_var1} 发布项目：${JOB_NAME} 发布环境：${ENV} 发布版本：${PROJECT_VERSION}.${PROJECT_PATCH} 第${BUILD_NUMBER}次构建' >>/data/packages/version_list.txt"
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

