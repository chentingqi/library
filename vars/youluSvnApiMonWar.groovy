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
                choices: ['test','reboot','stable','rollback-test']
            )
            string(name: 'APP_VERSION', defaultValue: "${map.APP_VERSION}",description: '构建成功后target目录下的版本号（test）')
        }
        stages{
            stage("拉取代码") {
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
            
            stage('代码构建'){
                when { anyOf { environment name: 'DEPLOY_TYPE', value: 'test' } }
                steps {
                    sh "echo ${map.MAVEN_BUILD_COMMAND}"
                    sh "${map.MAVEN_BUILD_COMMAND}"
                }
            }
            
            stage('清理 解压程序'){
                when { 
                    anyOf { 
                            environment name: 'DEPLOY_TYPE', value: 'test' ; 
                            environment name: 'DEPLOY_TYPE', value: 'rollback-test' 
                    } 
                }
                steps {
                    script {
                        if (params.DEPLOY_TYPE == "test" || params.DEPLOY_TYPE == "rollback-test") {
                               echo "deploy ${DEPLOY_TYPE} ${map.SERVER_IP}" 
                               sh "salt ${map.SERVER_IP} cp.get_file salt://${JOB_NAME}/${map.WAR} ${map.DEPLOY_DIR}/${map.WAR_NAME}"
                               sh "salt ${map.SERVER_IP} cmd.run '${map.UNWAR_COMMAND}'"
                        }
                    }   
                }
            }
            
            stage('配置 Test.js'){
                when { 
                    anyOf { 
                            environment name: 'DEPLOY_TYPE', value: 'test' ;  
                    } 
                }
                steps {
                    script {
                        echo "配置 Test.js" 
                        sh "salt ${map.SERVER_IP} cmd.run '${map.COPY_TESTJS_COMMAND}'"
                    }   
                }
            }
            
            stage('配置日志文件'){
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', value: 'test' ; 
                    } 
                }
                steps {
                    script {
                      echo "配置日志文件" 
                      sh "salt ${map.SERVER_IP} cmd.run '${map.COPY_LOGNC_COMMAND}'"
                    }   
                }
            }
            
            stage('重启服务'){
                when { 
                    anyOf { 
                            environment name: 'DEPLOY_TYPE', value: 'test' ; 
                            environment name: 'DEPLOY_TYPE', value: 'rollback-test' 
                            environment name: 'DEPLOY_TYPE', value: 'reboot'
                    } 
                }
                steps {
                    script {
                       echo "重启服务" 
                       sh "salt ${map.SERVER_IP} cmd.run '${map.REBOOT_COMMAND}'"
                    }   
                }
            }
            
            stage('日志输出'){
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', value: 'test' ;
                        environment name: 'DEPLOY_TYPE', value: 'rollback-test'
                        environment name: 'DEPLOY_TYPE', value: 'reboot'
                    } 
                }
                steps {
                    script {
                        if (params.DEPLOY_TYPE == "test" || params.DEPLOY_TYPE == "rollback-test" || params.DEPLOY_TYPE == "reboot") {
                           sh "sleep 5s"
                               echo "${map.SERVER_IP}：获取服务最后100行日志"
                               sh "salt ${map.SERVER_IP} cmd.run 'tail -n100 ${map.APP_LOG}'"
                        }
                        
                        sh "sleep 10s"
                    }
                }
            }
           
        }
        
       

    }
    
}
