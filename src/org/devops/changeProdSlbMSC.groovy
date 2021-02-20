package org.devops

def umountSlb(nodeip){
    //MSC管理端网关
    if (nodeip == '172.31.3.212'){
       try{
           sh """
              echo "卸载MSC节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-Remove172.31.3.212.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.3.213'){
       try{
           sh """
              echo "卸载MSC节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-Remove172.31.3.213.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
}

def mountSlb(nodeip){
    //MSC管理端网关
    if (nodeip == '172.31.3.212'){
       try{
           sh """
              echo "恢复MSC节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-Add172.31.3.212.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.3.213'){
       try{
           sh """
              echo "恢复MSC节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-Add172.31.3.213.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSC/MSC-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    
}