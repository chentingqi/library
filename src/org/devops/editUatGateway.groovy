package org.devops

def editUatGateway(service,ip){
    if (service == 'activitycenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ac.url 172.31.129.84 8330 activitycenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ac.url 172.31.129.79 8330 activitycenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ac.url 172.31.129.79 172.31.129.84 8330 activitycenter"""}
    }
    if (service == 'basedata'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.84 8300 basedata"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.79 8300 basedata"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.79 172.31.129.84 8300 basedata"""}
    }
    if (service == 'thirdpartyadapter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ta.url 172.31.129.84 8080 thirdpartyadapter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ta.url 172.31.129.79 8080 thirdpartyadapter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh ta.url 172.31.129.79 172.31.129.84 8080 thirdpartyadapter"""}
    }
    if (service == 'usercenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh uc.url 172.31.129.84 8000 usercenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh uc.url 172.31.129.79 8000 usercenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh uc.url 172.31.129.79 172.31.129.84 8000 usercenter"""}
    }
    if (service == 'paycenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh pc.url 172.31.129.84 8120 paycenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh pc.url 172.31.129.79 8120 paycenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh pc.url 172.31.129.79 172.31.129.84 8120 paycenter"""}
    }
}
