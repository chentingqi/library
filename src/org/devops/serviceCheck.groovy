package org.devops

def serviceCheck(deploytype,deployip,jobname,servicename){
    if (deploytype == 'ssh'){
       try{
          sh """
             
             """
       }catch (e){
            currentBuild.description="${servicename}服务检查失败!"
            error '${servicename}服务检查失败!'
        }
    }
    if (deploytype == 'salt'){
       try{
          sh """
             sleep 5s
             cp /data/build-devops/service_check.sh /data/jenkins_home/workspace/${jobname}/
             cp /data/build-devops/sls/service_check.sls /data/salt/service_check-${jobname}.sls
             sed -i 's/jarname/${servicename}/g'  service_check.sh
             sed -i 's/jobname/${jobname}/g' /data/salt/service_check-${jobname}.sls
             salt ${deployip} state.sls service_check-${jobname}
             """
       }catch (e){
            currentBuild.description="${servicename}服务检查失败!"
            error '${servicename}服务检查失败!'
        }
    }
}