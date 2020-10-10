package org.devops

def umountSlb(nodeip){
    if (nodeip == '192.168.10.83'){
       try{
           sh """
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/cjt/RemoveVServerGroupBackendServers-80-59.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.16.106.65'){
       try{
           sh """
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/cjt/RemoveVServerGroupBackendServers-80-65.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
}

def mountSlb(nodeip){
    if (nodeip == '192.168.10.83'){
       try{
           sh """
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/cjt/AddVServerGroupBackendServers-80-59.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.16.106.65'){
       try{
           sh """
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/cjt/AddVServerGroupBackendServers-80-65.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
}