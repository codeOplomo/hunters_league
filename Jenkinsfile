pipeline {
    agent {
        docker {
            image 'maven:3.8.8-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    environment {
        SONAR_PROJECT_KEY = "hunters_league"
        SONAR_TOKEN = "squ_3d95c730768d8d019b8524e885c78011f4f23690"
        SONAR_HOST_URL = "http://host.docker.internal:9000"
    }
    stages {
        stage('Install Tools') {
            steps {
                sh '''
                apt-get update && apt-get install -y jq apt-transport-https ca-certificates curl gnupg-agent software-properties-common
                curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
                add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
                apt-get update && apt-get install -y docker-ce-cli
                '''
            }
        }

        stage('Checkout Code') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: '*/main']],
                          userRemoteConfigs: [[url: 'https://github.com/codeOplomo/hunters_league']]
                ])
            }
        }

        stage('Debug Workspace') {
            steps {
                sh 'ls -R'
            }
        }

        stage('Build and SonarQube Analysis') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh """
                    mvn clean package sonar:sonar \
                      -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                      -Dsonar.host.url=$SONAR_HOST_URL \
                      -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Archive Test Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Quality Gate Check') {
            when {
                expression { currentBuild.result == null } // Only run if the previous stage succeeded
            }
            steps {
                script {
                    def qualityGate = sh(
                        script: """
                        curl -s -u "$SONAR_TOKEN:" \
                        "$SONAR_HOST_URL/api/qualitygates/project_status?projectKey=$SONAR_PROJECT_KEY" \
                        | jq -r '.projectStatus.status'
                        """,
                        returnStdout: true
                    ).trim()
                    if (qualityGate != "OK") {
                        error "Quality Gate failed! Stopping the build."
                    }
                }
            }
        }

        stage('Build Docker Image') {
            when {
                expression { currentBuild.result == null } // Only run if the previous stages succeeded
            }
            steps {
                script {
                    echo "Building Docker Image..."
                    sh 'docker build -t springboot-app .'
                }
            }
        }

        stage('Deploy Docker Container') {
            when {
                expression { currentBuild.result == null } // Only run if the previous stages succeeded
            }
            steps {
                sh """
                docker stop springboot-app-container || true
                docker rm springboot-app-container || true
                docker run -d -p 8080:8080 --name springboot-app-container springboot-app
                """
            }
        }
    }
    post {
        always {
            echo "Pipeline finished."
        }
        failure {
            echo "Pipeline failed. Check logs and archived results for more details."
        }
    }
}
