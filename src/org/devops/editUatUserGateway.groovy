package org.devops

def editUatUserGateway(service,ip){
    if (service == 'activitycenter'){
       if (ip == '172.31.129.84'){
           try{
               sh """sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.84 8330 activitycenter"""
               }
        }
       if (ip == '172.31.129.79'){
           try{
               sh """sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.79 8330 activitycenter"""   
           }
       }
       if (ip == '172.31.129.79-172.31.129.84'){
           try{sh """sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.79 172.31.129.84 8330 activitycenter"""
           }
       }
    }
}
