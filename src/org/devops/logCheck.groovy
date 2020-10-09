package org.devops

def logCheck(deploytype,deployip,jobname,logname){
    if (deploytype == 'ssh'){
       try{
          sh """
             
             """
       }catch (e){
            currentBuild.description="日志输出失败!"
            error '日志输出失败!'
        }
    }
    if (deploytype == 'salt'){
       try{
          sh """
             sh "sleep 5s"
             sh "cp /data/build-devops/log_check.sh /data/jenkins_home/workspace/${jobname}/"
             sh "cp /data/build-devops/sls/log_check.sls /data/salt/log_check-${jobname}.sls"
             sh "sed -i 's#log#${logname}#g' log_check.sh"
             sh "sed -i 's/jobname/${jobname}/g' /data/salt/log_check-${jobname}.sls"

             salt ${deployip} state.sls log_check-${jobname}
             """
       }catch (e){
            currentBuild.description="日志输出失败!"
            error '日志输出失败!'
        }
    }
}
