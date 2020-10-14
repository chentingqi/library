package org.devops

def nexusPush(packagetype,apppackage,nexusRep,nexusGroup,nexusPatch,nexusname1,nexusname2){
    try{
          sh """
            echo ${apppackage}
            cp /data/build-devops/nexus-common/nexus-upload.sh $workspace
            sed -i 's#name#${apppackage}#g' nexus-upload.sh
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

def nexusTime(packagetype,apppackage,nexusRep,version,nexusGroup,nexusname1,nexusname2){
    try{
          sh """
            echo ${apppackage}
            cp /data/build-devops/nexus-common/nexus-upload-time.sh $workspace
            sed -i 's#name#${apppackage}#g' nexus-upload-time.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload-time.sh
            sed -i 's#deployversion#${version}#g' nexus-upload-time.sh
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

def nexusTimeBook(packagetype,apppackage,nexusRep,nexusGroup,nexusPatch,nexusname1,nexusname2,book){
    try{
          sh """
            echo ${apppackage}
            cp /data/build-devops/nexus-common/nexus-upload-time1.sh $workspace
            sed -i 's#name#${apppackage}#g' nexus-upload-time1.sh
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

def nexusStable(packagetype,apppackage,nexusRep,version,nexusGroup,nexusname1,nexusname2){
    try{
          sh """
            echo ${apppackage}
            cp /data/build-devops/nexus-common/nexus-upload-stable.sh $workspace
            sed -i 's#name#${apppackage}#g' nexus-upload-stable.sh
            sed -i 's#cangku#${nexusRep}#g' nexus-upload-stable.sh
            sed -i 's#deployversion#${version}#g' nexus-upload-stable.sh
            sed -i 's#group#${nexusGroup}#g' nexus-upload-stable.sh
            sed -i 's/one/${nexusname1}/g' nexus-upload-stable.sh
            sed -i 's/two/${nexusname2}/g' nexus-upload-stable.sh
            sed -i 's/type/${packagetype}/g' nexus-upload-stable.sh
            sh nexus-upload-stable.sh
             """
       }catch (e){
            currentBuild.description="制品上传失败!"
            error '制品上传失败!'
        }
}
