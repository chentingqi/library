package org.devops

def deployService(deploytype,deploypackage,deployip,srcdir,deploydir,deployshell){
    if (deploytype == 'ssh'){
       try{
          sh """
             scp ${deploypackage} ${deployip}:/${deploydir}
             ssh -p 22 ${deployip} 'sh ${deployshell}'
             """
       }catch (e){
            currentBuild.description="服务部署失败!"
            error '服务部署失败!'
        }
    }
    if (deploytype == 'salt'){
       try{
          sh """
             salt ${deployip} cp.get_file salt://${srcdir}/${deploypackage} ${deploydir}
             salt ${deployip} cmd.run 'sh ${deployshell}'
             """
       }catch (e){
            currentBuild.description="服务部署失败!"
            error '服务部署失败!'
        }
    }
}