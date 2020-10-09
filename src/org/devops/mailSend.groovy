package org.devops

def mailSend(status,maillist){
    if (status == 'success'){
       try {
          emailext attachLog: true, attachmentsPattern: 'target/cucumber.html', 
          subject: "'${ENV}环境：${env.JOB_NAME} [${env.BUILD_NUMBER}]' 构建成功",
		  body: """
          <div id="content">
          <h1>CI报告</h1>
          <div id="sum2">
	      <h2>构建结果 - <font color="#00FF00">SUCCESS</font></h2>
          <ul>
          <li>项目名称 : <a>${env.JOB_NAME}</a></li>
          <li>项目视图 : <a href="http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline">http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline</a></li>
	      <li>项目 URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
	      <li>构建编号 : 第${BUILD_NUMBER}次构建</li>
          <li>构建日志 ：<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
	      <li>代码扫描 ：<a href="http://192.168.15.178:9000/dashboard?id=${JOB_NAME}">http://192.168.15.178:9000/dashboard?id=${JOB_NAME}</a></li>
          </ul>
          </div>
          </div>""",mimeType: 'text/html',to : "${maillist}",from: "cjt@youlu.com"
        }
    }
    if (status == 'failure'){
       try {
          emailext attachLog: true, attachmentsPattern: 'target/cucumber.html', 
          subject: "'${ENV}环境：${env.JOB_NAME} [${env.BUILD_NUMBER}]' 构建失败",
		  body: """
          <div id="content">
          <h1>CI报告</h1>
          <div id="sum2">
	      <h2>构建结果 - <font color="#FF0000">FAILED</font></h2>
          <ul>
          <li>项目名称 : <a>${env.JOB_NAME}</a></li>
          <li>项目视图 : <a href="http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline">http://192.168.10.82/blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.JOB_NAME}/${BUILD_NUMBER}/pipeline</a></li>
	      <li>项目 URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
	      <li>构建编号 : 第${BUILD_NUMBER}次构建</li>
          <li>构建日志 ：<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
	      <li>代码扫描 ：<a href="http://192.168.15.178:9000/dashboard?id=${JOB_NAME}">http://192.168.15.178:9000/dashboard?id=${JOB_NAME}</a></li>
          </ul>
          </div>
          </div>""",mimeType: 'text/html',to : "${maillist}",from: "cjt@youlu.com"
        }
    }
    

}