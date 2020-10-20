package org.devops

def singleGateway(gateway,keyname,nodeip,nodeport,nodename){
    if (gateway == 'MSU'){
       try{
           sh """
              echo "修改MSU用户端网关配置"
              sh /data/GATEWAY/user_edit_fgateway.sh ${keyname} ${nodeip} ${nodeport} ${nodename}
           """
        }catch(e){
             currentBuild.description="用户端网关修改配置失败!"
             echo "用户端网关修改配置失败!"
        }
    }
    if (gateway == 'MSA'){
       try{
           sh """
              echo "修改MSA管理端网关配置"
              sh /data/GATEWAY/user_edit_fgateway.sh ${keyname} ${nodeip} ${nodeport} ${nodename}
           """
        }catch(e){
             currentBuild.description="管理端网关修改配置失败!"
             echo "管理端网关修改配置失败!"
        }
    }
}

def moreGateway(gateway,keyname,nodeip1,nodeip2,nodeport,nodename){
    if (gateway == 'MSU'){
       try{
           sh """
              echo "修改MSU用户端网关配置"
              sh /data/GATEWAY/user_edit_fgateway.sh ${keyname} ${nodeip1} ${nodeip2} ${nodeport} ${nodename}
           """
        }catch(e){
             currentBuild.description="用户端网关修改配置失败!"
             echo "用户端网关修改配置失败!"
        }
    }
    if (gateway == 'MSA'){
       try{
           sh """
              echo "修改MSA管理端网关配置"
              sh /data/GATEWAY/user_edit_fgateway.sh ${keyname} ${nodeip1} ${nodeip2} ${nodeport} ${nodename}
           """
        }catch(e){
             currentBuild.description="管理端网关修改配置失败!"
             echo "管理端网关修改配置失败!"
        }
    }
}


