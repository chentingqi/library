package src/org/devops

def gitPull(git_url,git_branch,git_cert){
    git branch: "${git_url}", credentialsId: "${git_cert}", url: "${git_branch}"
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