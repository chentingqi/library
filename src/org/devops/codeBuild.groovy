package org.devops

//构建打包


def codeBuild(javaVersion,buildType,buildDir,buildShell){
    if (buildType == 'maven'){
        buildHome = "/data/apache-maven-3.6.3"
    } else if (buildType == 'ant'){
        buildHome = "/data/ant"
    } else if (buildType == 'gradle'){
        buildHome = '/data/gradle'
    } else{
        error 'buildType Error [maven|ant|gradle]'
    }
    echo "BUILD_HOME: ${buildHome}"
    
    //选择JDK版本
    jdkPath = ['jdk7' : '/data/jdk1.7.0_79',
               'jdk6' : '/data/jdk1.6.0_45',
               'jdk8' : '/data/jdk1.8.0_201',
               'jdk11': '/data/jdk-11.0.1',
               'null' : '/data/jdk1.8.0_201']
    def javaHome = jdkPath["$javaVersion"]
    if ("$javaVersion" == 'jdk11'){
       sh  """
        export JAVA_HOME=${javaHome}
        export PATH=\$JAVA_HOME/bin:\$PATH
        java -version
        cd ${buildDir} && ${buildHome} ${buildShell}
        """
    } else {
        sh  """
            export JAVA_HOME=${javaHome}
            export BUILD_HOME=${buildHome}
            export PATH=\$JAVA_HOME/bin:\$BUILD_HOME/bin:\$PATH
            export CLASSPATH=.:\$JAVA_HOME/lib/dt.jar:\$JAVA_HOME/lib/tools.jar
            java -version
            cd ${buildDir} && ${buildShell}
            """
    }
}


//前端Build
def WebBuild(srcDir,serviceName){
    def deployPath = "/srv/salt/${JOB_NAME}"
    sh """
        [ -d ${deployPath} ] || mkdir -p ${deployPath}
        cd ${srcDir}/
        rm -fr *.tar.gz 
        tar zcf ${serviceName}.tar.gz * 
        cp ${serviceName}.tar.gz ${deployPath}/${serviceName}.tar.gz
        cd -
    """
}