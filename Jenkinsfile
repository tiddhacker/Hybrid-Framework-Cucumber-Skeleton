pipeline {
    agent any
	
	tools {
        
        maven "MAVEN_HOME"
        jdk "JAVA_HOME"
    }
    
    stages {
	
	
        stage('Preparing Environment') {
            steps {
                echo "${params.JAVA_HOME}"
                echo "Preparing environment"
                echo "Browser is ${params.Browser}"
                echo "Tags is ${params.Tags}"
                echo "Platform is ${params.Platform}"
                echo "BranchName is ${params.BranchName}"
				echo "Run on Sauce Labs Cloud set to ${params.RemoteHubExecution}"

				script{
					//cucumber tags
					if(params.Tags.isEmpty()){
					params.Tags="@UI"
					}
					
					//branch
					if(params.BranchName.isEmpty()){
					 params.BranchName="origin/main"
					}
				}
            }
        }
        
        
        stage('Code Checkout'){
			steps{
			
				checkout changelog: false, poll: false,
				scm: [$class: 'GitSCM', branches: [[name: '*/$BranchName']],
				extensions: [],
				userRemoteConfigs: [[credentialsId: '380c6421-ace2-4377-acf8-725ed47d6635',
				url: 'https://github.com/tiddhacker/Hybrid-Framework-Cucumber-Skeleton.git']]]
			
			}
		}
		
		
		stage('Executing Test'){
		    steps{
		        echo "Executing Test"
                bat 'mvn clean compile test -f automation/pom.xml -Dcucumber.filter.tags="%Tags%" -Dbrowser=%Browser% -Dplatform=%Platform% -DRemoteHubExecution=%RemoteHubExecution% -DTestRunner=testRunner.java'
		    }
		}
    }
	
	post{
		always{
		
			//bat 'timeout 20'
            		archiveArtifacts artifacts: 'automation/target/report/**', followSymlinks: false
			
			publishHTML([allowMissing: false,
			alwaysLinkToLastBuild: true,
			keepAll: true,
			reportDir: 'automation/target/report',
			reportFiles: 'report.html',
			reportName: 'Automation Test Report',
			reportTitles: 'Automation Test Report'])
			
			emailext attachmentsPattern: 'automation/target/report/**',
			body: 'Pipeline Completed For $PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS: Check console output at $BUILD_URL to view the results.',
			subject: 'Pipeline Build Notification : $PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
			to: 'sounakghosh62@gmail.com,sounakghosh62@outlook.com'
			
		}
	
	}
}
