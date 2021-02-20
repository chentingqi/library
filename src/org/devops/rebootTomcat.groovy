package org.devops

def rebootTomcat(service){
    //活动中心
    if (service == 'activitycenter01'){
       sh """salt 172.31.129.166 cmd.run 'sh /data/packages/reboot_tomcat.sh tomcat-activitycenter-833 restart'"""
    }
    if (service == 'activitycenter02'){
       sh """salt 172.31.129.167 cmd.run 'sh /data/packages/reboot_tomcat.sh tomcat-activitycenter-833 restart'"""
    }
    //基础数据
    if (service == 'basedata'){
       if (ip == 'IP1'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.84 8300 basedata"""}
       if (ip == 'IP2'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.79 8300 basedata"""}
       if (ip == 'IP12'){sh """sh /data/GATEWAY/uat_edit_fgateway.sh bd.url 172.31.129.79 172.31.129.84 8300 basedata"""}
    }
    

}
