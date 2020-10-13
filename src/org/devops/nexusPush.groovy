package org.devops

def nexusPush(packagetype,package,nexusRep,nexusGroup,nexusPatch,nexusname1,nexusname2){
    try{
          sh """
            echo ${package}
            cp /data/build-devops/nexus-common/nexus-upload.sh $workspace
            sed -i 's#name#${package}#g' nexus-upload.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload.sh
            sed -i 's#group#${nexusGroup}#g' nexus-upload.sh
            sed -i 's#patch#${nexusPatch}#g' nexus-upload.sh
            sed -i 's/one/${nexusname1}/g' nexus-upload.sh
            sed -i 's/two/${nexusname2}/g' nexus-upload.sh
            sed -i 's/id/${nexusname1}-${nexusname2}/g' nexus-upload.sh
            sed -i 's/type/${packagetype}/g' nexus-upload.sh
            sh nexus-upload.sh
             """
       }catch (e){
            currentBuild.description="制品上传失败!"
            error '制品上传失败!'
        }
}

def nexusTime(packagetype,package,nexusRep,nexusGroup,nexusname1,nexusname2){
    try{
          sh """
            echo ${package}
            cp /data/build-devops/nexus-common/nexus-upload-time.sh $workspace
            sed -i 's#name#${package}#g' nexus-upload-time.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload-time.sh
            sed -i 's#group#${nexusGroup}#g' nexus-upload-time.sh
            sed -i 's/one/${nexusname1}/g' nexus-upload-time.sh
            sed -i 's/two/${nexusname2}/g' nexus-upload-time.sh
            sed -i 's/id/${nexusname1}-${nexusname2}/g' nexus-upload-time.sh
            sed -i 's/type/${packagetype}/g' nexus-upload-time.sh
            sh nexus-upload-time.sh
             """
       }catch (e){
            currentBuild.description="制品上传失败!"
            error '制品上传失败!'
        }
}

def nexusTimeBook(packagetype,package,nexusRep,nexusGroup,nexusPatch,nexusname1,nexusname2,book){
    try{
          sh """
            echo ${package}
            cp /data/build-devops/nexus-common/nexus-upload-time1.sh $workspace
            sed -i 's#name#${package}#g' nexus-upload-time1.sh
            sed -i 's#book#${book}#g' nexus-upload-time1.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload-time1.sh
            sed -i 's#group#${nexusGroup}#g' nexus-upload-time1.sh
            sed -i 's#patch#${nexusPatch}#g' nexus-upload-time1.sh
            sed -i 's/one/${nexusname1}/g' nexus-upload-time1.sh
            sed -i 's/two/${nexusname2}/g' nexus-upload-time1.sh
            sed -i 's/id/${nexusname1}-${nexusname2}/g' nexus-upload-time1.sh
            sed -i 's/type/${packagetype}/g' nexus-upload-time1.sh
            sh nexus-upload-time1.sh
             """
       }catch (e){
            currentBuild.description="制品上传失败!"
            error '制品上传失败!'
        }
}
