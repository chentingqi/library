package org.devops

def uploadSvn(appversion,apppatch,nexusname,apppackage){
    try{
          sh """
            echo ${apppackage}
            # 清除缓存
            rm -rf yunwei
            ## 创建文件夹
            mkdir -pv yunwei
            ## 进入文件夹
            cd yunwei
            ## 下载一个空的文件夹
            svn checkout --depth=empty http://192.168.11.247/svn/youlu/MicroService --username=chenjingtao --password=cjt#2020
            ## 
            cd MicroService

            ## 查询是否有重复的版本
            svn up --set-depth=immediates ${appversion}/${apppatch} --username=chenjingtao --password=cjt#2020

            ## 创建版本
            mkdir -pv ${appversion}/${apppatch}
            ## 进入版本
            cd ${appversion}/${apppatch}

            ## 查询是否有重复的项目
            svn up ${nexusname} --username=chenjingtao --password=cjt#2020

            ## 创建版本
            mkdir -pv ${nexusname}
            ## 进入版本
            cd ${nexusname}

            ## add war
            cp ${WORKSPACE}/${apppackage} .
            ## ${nexusname}
            cd ../
            # 添加所有文件
            svn add . --no-ignore --force
            svn commit -m "Update ${nexusname} TO '${appversion}/${apppatch}'" --username=chenjingtao --password=cjt#2020

             """
       }catch (e){
            currentBuild.description="制品上传SVN失败!"
            error '制品上传SVN失败!'
        }
}
