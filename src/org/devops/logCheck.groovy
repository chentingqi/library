package org.devops

def logCheck(deploytype,deployip,srcdir){
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
             salt ${deployip} state.sls log_check-${srcdir}
             """
       }catch (e){
            currentBuild.description="日志输出失败!"
            error '日志输出失败!'
        }
    }
}
