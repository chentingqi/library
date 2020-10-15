package org.devops

def rollback(env,appversion,apppatch,packagename){
    if (rollback-uat == 'rollback-uat'){
       try{
          sh """
             
             """
       }catch (e){
            currentBuild.description="${packagename}:${appversion}.${apppatch}版本回滚失败!"
            error '${packagename}:${appversion}.${apppatch}版本回滚失败!'
        }
    }
    if (rollback-uat == 'rollback-prod'){
       try{
          sh """
             
             """
       }catch (e){
            currentBuild.description="${packagename}:${appversion}.${apppatch}版本回滚失败!"
            error '${packagename}:${appversion}.${apppatch}版本回滚失败失败!'
        }
    }
}
