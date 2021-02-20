// youluSvnNicelooPcUI.groovy

def call(map) {

    pipeline {
        agent {
                label "${map.node}"
        }
        options {
            timestamps()  //日志会有时间
            skipDefaultCheckout()  //删除隐式checkout scm语句
            disableConcurrentBuilds() //禁止并行
            // buildDiscarder(logRotator(numToKeepStr: '5')) 
            timeout(time: 1, unit: 'HOURS')  //流水线超时设置1h
    
        }
        environment {
            examples_var1 = sh(script: 'echo "当前的时间是: `date`"', returnStdout: true).trim()
        }
        
        parameters {
            choice(
                description: '选择发布操作类型',
                name: 'DEPLOY_TYPE',
                choices: ['test','rollback-test']
            )
        }
        stages{
            stage("拉取前端NicelooPcUI代码") {
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', 
                        value: 'test'
                    }
                }
                steps {
                    sh "echo code pull"
                    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', 
                    excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, 
                    ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, 
                    credentialsId: "${map.svn_cert}", depthOption: 'infinity', ignoreExternalsOption: true, local: '.', 
                    remote: "${map.svn_url}"]], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
                    
                }
            }
    
            // stage('代码扫描'){
            //     when { 
            //         anyOf { 
            //             environment name: 'DEPLOY_TYPE', value: 'test' 
            //         }
            //     }
            //     steps {
            //          withSonarQubeEnv('sonar') {
            //              sh "echo SONAR启动 "
            //              sh "cp /data/build-devops/sonar-project.properties $workspace"
            //              sh "sed -i 's/jobname/${JOB_NAME}/g' sonar-project.properties"
            //              sh "sed -i 's#workspace#$workspace#g' sonar-project.properties"
            //              //sh "/data/sonar-scanner-3.0.0.702-linux/bin/sonar-scanner -X -Dsonar.host.url=http://172.16.106.88:9000"
            //          }
            //     }
            // }
            
            stage('构建前端NicelooPcUI代码'){
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', value: 'test' 
                    }
                }
                steps {
                    sh "echo ${map.NPM_BUILD_COMMAND}"
                    sh "${map.NPM_BUILD_MIRROR}"
                    sh "${map.NPM_BUILD_ENV}"
                    sh "${map.NPM_BUILD_COMMAND}"
                }
            }

            stage('部署前端NicelooPc节点'){
                when { 
                    anyOf { 
                            environment name: 'DEPLOY_TYPE', value: 'test' ; 
                    } 
                }
                steps {
                    script {
                       sh "sh /data/version_list/mark_version.sh ${APP_NAME} ${DEPLOY_VERSION}"
                       echo "deploy ${DEPLOY_TYPE} ${map.TEST_IP1}"
                       sh "salt ${map.TEST_IP1} cp.get_file salt://${JOB_NAME}/${map.ZIP} ${map.DEPLOY_DIR}/${map.ZIP_NAME}"
                       sh "salt ${map.TEST_IP1} cmd.run '${map.DEPLOY_COMMAND}'"
                    }   
                }
            }
        
            stage('设置GlobalConfig.js'){
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', value: 'test' 
                    }
                }
                steps {
                    sh "echo ${map.COPY_GLOBAL_CONFIG_COMMAND}"
                    sh "salt ${map.TEST_IP1} cmd.run '${map.COPY_GLOBAL_CONFIG_COMMAND}'"
                }
            }
            
            stage('设置Nginx流量切换'){
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', value: 'test' ;
                        environment name: 'DEPLOY_TYPE', value: 'rollback-test'
                    }
                }
                steps {
                    script {
                    if (params.DEPLOY_TYPE == "test") {
                    sh "echo ${map.NGINX_CHANGE_COMMAND}"
                    sh "salt ${map.TEST_IP1} cmd.run '${map.NGINX_CHANGE_COMMAND}'"
                    }
                    if (params.DEPLOY_TYPE == "rollback-test") {
                    sh "echo ${map.NGINX_ROLLBACK_COMMAND}"
                    sh "salt ${map.TEST_IP1} cmd.run '${map.NGINX_ROLLBACK_COMMAND}'"
                    }
                    }
                }
            }
            
            /*stage('上传SVN制品库'){
            when { 
                anyOf { environment name: 'DEPLOY_TYPE', value: 'stable' 
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

            stage('上传NEXUS制品库'){
            when { 
                anyOf { environment name: 'DEPLOY_TYPE', value: 'stable' 
                      } 
                }
            steps{
            sh "cp /data/build-devops/nexus-common/nexus_upload.sh $workspace"
            sh "sed -i 's#bao#${map.WAR}#g' nexus_upload.sh"
            sh "sed -i 's/cangku/youlu-yunwei/g' nexus_upload.sh"
            sh "sed -i 's/group/${SVN_APP_NAME}/g' nexus_upload.sh"
            sh "sed -i 's/version/${PROJECT_VERSION}/g' nexus_upload.sh"
            sh "sed -i 's/patch/${PROJECT_PATCH}/g' nexus_upload.sh"
            sh "sed -i 's/id/${APP_lower_NAME}/g' nexus_upload.sh"
            sh "sed -i 's/type/war/g' nexus_upload.sh"
            sh "sh nexus_upload.sh"
            }
            }*/


        //构建后操作
        }
        
        /*post {
        always {
            script{
                println("always")
            }
        }

        success {
            script{
                currentBuild.description = "\n ${DEPLOY_TYPE}-${PROJECT_VERSION}-${PROJECT_PATCH}构建成功!" 
            }
        }

        failure {
            script{
                currentBuild.description = "\n ${DEPLOY_TYPE}-${PROJECT_VERSION}-${PROJECT_PATCH}构建失败!" 
            }
        }

        aborted {
            script{
                currentBuild.description = "\n ${DEPLOY_TYPE}-${PROJECT_VERSION}-${PROJECT_PATCH}构建取消!" 
            }
        }
    }*/

    }
    
}
