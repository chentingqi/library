// youluSvnSql.groovy

def call(map) {

    pipeline {
        agent {
                label "${map.node}"
        }
        options {
            timestamps()  //日志会有时间
            skipDefaultCheckout()  //删除隐式checkout scm语句
            disableConcurrentBuilds() //禁止并行
            timeout(time: 1, unit: 'HOURS')  //流水线超时设置1h
    
        }
        environment {
            examples_var1 = sh(script: 'echo "当前的时间是: `date`"', returnStdout: true).trim()
            SQL_FILE_NAME= sh(script: 'echo "$(cat /proc/sys/kernel/random/uuid).sql"', returnStdout: true).trim()
        }
        
        parameters {
            choice(
                description: '选择发布操作类型',
                name: 'DEPLOY_TYPE',
                choices: ['test']
            )
        }
        stages{
            stage("下载SQL脚本到服务器") {
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', 
                        value: 'test'
                    }
                }
                steps {
                    echo "目标服务器下载SQL脚本"
                    echo "sh /data/packages/download_sql.sh ${map.SQL_URL} ${SQL_FILE_NAME}"
                    sh "salt ${map.MYSQL_IP} cmd.run 'sh /data/packages/download_sql.sh ${map.SQL_URL} ${SQL_FILE_NAME}'"
                }
            }
            
            stage('执行SQL语句'){
                when { 
                    anyOf { 
                        environment name: 'DEPLOY_TYPE', value: 'test' 
                    }
                }
                steps {
                    sh "echo ${map.EXEC_SQL_COMMAND}"
                    sh "salt ${map.MYSQL_IP} cmd.run '${map.EXEC_SQL_COMMAND} ${map.MYSQL_PORT} ${map.DB_NAME} ${SQL_FILE_NAME}'"
                }
            }
        }
    }
}
