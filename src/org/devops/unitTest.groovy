package org.devops

def unitTest(){
    try{
       sh """
          export MAVEN_HOME=/data/apache-maven-3.6.3
          export PATH=\$MAVEN_HOME/bin:\$PATH
          mvn test
       """
    }catch (e){
            currentBuild.description="单元测试失败!"
            error '单元测试失败!'
               }

}