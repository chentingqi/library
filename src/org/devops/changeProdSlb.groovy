package org.devops

def umountSlb(nodeip){
    //MSA管理端网关
    if (nodeip == '172.31.3.130'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-Remove172.31.3.130.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.3.85'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-Remove172.31.3.85.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.4.6'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-Remove172.31.4.6.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    //MSU管理端网关
    if (nodeip == '172.31.3.84'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Remove172.31.3.84.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Remove172.31.3.84.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.3.129'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Remove172.31.3.129.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Remove172.31.3.129.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.4.110'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Remove172.31.4.110.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Remove172.31.4.110.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.4.111'){
       try{
           sh """
              echo "卸载MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Remove172.31.4.111.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Remove172.31.4.111.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    
    //MSO对外端网关
    if (nodeip == '172.31.3.196'){
       try{
           sh """
              echo "卸载MSO节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-Remove172.31.3.196.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    if (nodeip == '172.31.3.197'){
       try{
           sh """
              echo "卸载MSO节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-Remove172.31.3.197.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="从SLB上卸载${nodeip}失败!"
             echo "从SLB上卸载${nodeip}失败"
        }
    }
    
}

def mountSlb(nodeip){
    //MSA管理端网关
    if (nodeip == '172.31.3.130'){
       try{
           sh """
              echo "恢复MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-Add172.31.3.130.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.3.85'){
       try{
           sh """
              echo "恢复MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-Add172.31.3.85.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.4.6'){
       try{
           sh """
              echo "恢复MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-Add172.31.4.6.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSA/MSA-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    //MSU管理端网关
    if (nodeip == '172.31.3.84'){
       try{
           sh """
              echo "恢复MSU节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Add172.31.3.84.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Add172.31.3.84.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.3.129'){
       try{
           sh """
              echo "恢复MSU节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Add172.31.3.129.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Add172.31.3.129.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.4.110'){
       try{
           sh """
              echo "恢复MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Add172.31.4.110.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Add172.31.4.110.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.4.111'){
       try{
           sh """
              echo "恢复MSA节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-Add172.31.4.111.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-Add172.31.4.111.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-1-DescribeHealthStatus.py
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSU/MSU-2-DescribeHealthStatus.py
              
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    
    //MSO对外端网关
    if (nodeip == '172.31.3.196'){
       try{
           sh """
              echo "恢复MSO节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-Add172.31.3.196.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    if (nodeip == '172.31.3.197'){
       try{
           sh """
              echo "恢复MSO节点：${nodeip}"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-Add172.31.3.197.py
              echo "查看当前节点"
              python /data/SLB/aliyun-python-sdk-slb-3.2.18/prod/MSO/MSO-DescribeHealthStatus.py
           """
        }catch(e){
             currentBuild.description="向SLB上增加服务器${nodeip}失败!"
             echo "向SLB上增加服务器${nodeip}失败!"
        }
    }
    
}