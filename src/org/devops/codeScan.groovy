package org.devops


//代码扫描
def codeScan(projectType,srcDir,serviceName){
    def scanHome = "/data/sonar-scanner-3.0.0.702-linux"
    if (projectType == 'java'){
        try {
            sh """
                cd ${srcDir} 
                cp /data/build-devops/sonar-project.properties ${srcDir}
                sed -i 's/jobname/${serviceName}/g' sonar-project.properties
                ${scanHome}/bin/sonar-scanner -X -Dsonar.scm.disabled=true -Dsonar.host.url=http://192.168.10.83:9000
             """ 
        } catch (e){
            currentBuild.description="代码扫描失败!"
            error '代码扫描失败!'
        }
    }
    
    if (projectType == 'c'){
        try {
            sh """
                cd ${srcDir} 
                cp /data/build-devops/sonar-project.properties ${srcDir}
                sed -i 's/jobname/${serviceName}/g' sonar-project.properties
                sed -i 's/sonar.language=java/sonar.language=c/g' sonar-project.properties
                ${scanHome}/bin/sonar-scanner -X -Dsonar.scm.disabled=true -Dsonar.host.url=http://192.168.10.83:9000
             """ 
        } catch (e){
            currentBuild.description="代码扫描失败!"
            error '代码扫描失败!'
        }
    }
    
    else if (projectType == 'web'){
        try {
            sh  """
                cd ${srcDir}
                cp /data/build-devops/sonar-project.properties ${srcDir}
                sed -i 's/jobname/${serviceName}/g' sonar-project.properties
                sed -i 's/sonar.language=java/sonar.language=js/g' sonar-project.properties
                ${scanHome}/bin/sonar-scanner -X -Dsonar.scm.disabled=true -Dsonar.host.url=http://192.168.10.83:9000  
                cd - 
                """
        } catch (e){
            currentBuild.description="代码扫描失败!"
            error '代码扫描失败!'
        }
    }
}