package org.devops

def editUatUserGateway(service,ip){
    if (service == 'activitycenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.84 8330 activitycenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.79 8330 activitycenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/user_edit_fgateway.sh ac.url 172.31.129.79 172.31.129.84 8330 activitycenter"""}
    }
    if (service == 'basedata'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/user_edit_fgateway.sh bd.url 172.31.129.84 8300 basedata"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/user_edit_fgateway.sh bd.url 172.31.129.79 8300 basedata"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/user_edit_fgateway.sh bd.url 172.31.129.79 172.31.129.84 8300 basedata"""}
    }
}
