package org.devops

def activitycenter(service){
    if (service == '172.31.129.84'){
       try{
           sh """
              sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.84 8330 activitycenter
           """
        }catch(e){
             currentBuild.description="修改网关配置${service}失败!"
             echo "修改网关配置${service}失败!"
        }
    }
    if (service == '172.31.129.79'){
       try{
           sh """
              sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.79 8330 activitycenter
           """
        }catch(e){
             currentBuild.description="修改网关配置${service}失败!"
             echo "修改网关配置${service}失败!"
        }
    }
    if (service == '172.31.129.79,172.31.129.84'){
       try{
           sh """
              sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.79 172.31.129.84 8330 activitycenter
           """
        }catch(e){
             currentBuild.description="修改网关配置${service}失败!"
             echo "修改网关配置${service}失败!"
        }
    }
}
