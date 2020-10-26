package org.devops

def editProdGateway(service,ip){
    if (service == 'activitycenter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ac.url 172.31.3.83 8330 activitycenter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ac.url 172.31.3.124 8330 activitycenter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ac.url 172.31.3.83 172.31.3.124 8330 activitycenter"""}
    }
    if (service == 'basedata'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh bd.url 172.31.3.83 8300 basedata"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh bd.url 172.31.3.124 8300 basedata"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh bd.url 172.31.3.83 172.31.3.124 8300 basedata"""}
    }
    if (service == 'thirdpartyadapter'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ta.url 172.31.3.83 8080 thirdpartyadapter"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ta.url 172.31.3.124 8080 thirdpartyadapter"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/prod_edit_fgateway.sh ta.url 172.31.3.83 172.31.3.124 8080 thirdpartyadapter"""}
    }
}
