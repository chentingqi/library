package org.devops

def nexusPush(packagetype,jar,nexusRep,nexusGroup,nexusPatch,nexusname1,nexusname2,nexusname){
    try{
          sh """
            echo ${jar}
            cp /data/build-devops/nexus-common/nexus-upload.sh $workspace
            sed -i 's#name#${jar}#g' nexus-upload.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload.sh
            sed -i 's#group#${nexusGroup}#g' nexus-upload.sh
            sed -i 's#patch#${nexusPatch}#g' nexus-upload.sh
            sed -i 's/one/${nexusname1}/g' nexus-upload.sh
            sed -i 's/two/${nexusname2}/g' nexus-upload.sh
            sed -i 's/id/${nexusname}/g' nexus-upload.sh
            sed -i 's/type/packagetype/g' nexus-upload.sh
            sh nexus-upload.sh
             """
       }catch (e){
            currentBuild.description="制品上传失败!"
            error '制品上传失败!'
        }
}

def nexusPushTime(packagetype,jar,nexusRep,nexusGroup,nexusPatch,nexusname1,nexusname2,nexusname){
    try{
          sh """
            echo ${jar}
            cp /data/build-devops/nexus-common/nexus-upload-time.sh $workspace
            sed -i 's#name#${jar}#g' nexus-upload-time.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload-time.sh
            sed -i 's#group#${nexusGroup}#g' nexus-upload-time.sh
            sed -i 's#patch#${nexusPatch}#g' nexus-upload-time.sh
            sed -i 's/one/${nexusname1}/g' nexus-upload-time.sh
            sed -i 's/two/${nexusname2}/g' nexus-upload-time.sh
            sed -i 's/id/${nexusname}/g' nexus-upload-time.sh
            sed -i 's/type/packagetype/g' nexus-upload-time.sh
            sh nexus-upload-time.sh
             """
       }catch (e){
            currentBuild.description="制品上传失败!"
            error '制品上传失败!'
        }
}
