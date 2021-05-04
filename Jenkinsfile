node {
jdk = tool name: 'Java_8_221', type: 'jdk'
   env.JAVA_HOME = "${jdk}"
   def mvnHome
   catchError {
      stage('Build') {
        // Run the maven build
        configFileProvider([configFile(fileId: 'ae5eda6d-e166-4b43-a78c-ed67ed10cac0', variable: 'MAVEN_SETTINGS')]) {
          def maven = docker.image("maven:3.8.1-openjdk-8-slim")
	  	  maven.pull()
		  maven.inside("-v ${jdk}:${jdk}") {
		    sh 'mvn -X --global-settings ${MAVEN_SETTINGS} clean install'
		  }
        }
      }
   } 
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archiveArtifacts '**/target/*.jar'
//      archiveArtifacts '**/target/*.zip'
   }

   def previousResult = currentBuild.previousBuild?.result
   if(currentBuild.currentResult != 'SUCCESS') {
      //emailext attachLog: true, to: 'skltp@kentor.se', recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: "Build failed in Jenkins: ${env.JOB_NAME}#${currentBuild.number}", body: "See ${env.BUILD_URL} for details."
      emailext attachLog: true, recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: "Build failed in Jenkins: ${env.JOB_NAME}#${currentBuild.number}", body: "See ${env.BUILD_URL} for details."
   }
   else if (currentBuild.currentResult == 'SUCCESS' && previousResult && previousResult != currentBuild.currentResult) {
      //emailext attachLog: true, to: 'skltp@kentor.se', recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: "${env.JOB_NAME} back to stable", body: "See ${env.BUILD_URL} for details."
      emailext attachLog: true, recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: "${env.JOB_NAME} back to stable", body: "See ${env.BUILD_URL} for details."
   }
}