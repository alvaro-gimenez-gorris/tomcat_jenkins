node('main'){	
	stage('Code Checkout'){
		checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '0dc4562c-e6a8-41e7-a464-95a3401aab54', url: 'https://github.com/alvaro-gimenez-gorris/tomcat_jenkins']])
	}
  stage('Build'){
		sh "mvn clean install -Dmaven.test.skip=true"
  }
	stage('Test Cases Execution'){
		sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agenct install -Pcoverage-per-test"
  }
	stage('Sonar Analysis'){
		// sh "mvn sonar:sonar -Dsonar.host.url:http://ip:puerto -Dsonar.login=id"
  }
  stage('Archive Artifacts'){
	  archiveArtifacts artifacts 'target/*.war'
  }
	stage('Code Deployment'){
		deploy adapters: [tomcat9(credentialsId: 'TomcatCreds', path: '', url: 'http://127.0.0.1:8882/')], contextPath: 'curso_jenkins', onFailure: false, war: 'target/*.war'
	}
  stage('Notification'){
		//emailext (
      //subject: "Job completed"
      //body: "Jenkins Pipeline Job for Maven Build got completed"
      //to: "email@email.com"
    //)
  }
}
