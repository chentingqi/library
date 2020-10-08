package src.org.devops

def gitPull(giturl,gitbranch,gitcert){
    git branch: 'gitbranch', credentialsId: 'gitcert', url: 'giturl'
}

def svnPull(svn_url,svn_cert){
    checkout([$class: 'SubversionSCM', 
              additionalCredentials: [], 
              excludedCommitMessages: '', 
              excludedRegions: '', 
              excludedRevprop: '', 
              excludedUsers: '', 
              filterChangelog: false, 
              ignoreDirPropChanges: false, 
              includedRegions: '', 
              locations: [[cancelProcessOnExternalsFail: true, 
              credentialsId: "${svn_cert}", 
              depthOption: 'infinity', 
              ignoreExternalsOption: true, local: '.', 
              remote: "${svn_url}"]], 
              quietOperation: true, 
              workspaceUpdater: [$class: 'UpdateUpdater']])
}