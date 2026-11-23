
pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        IMAGE_NAME = "leave-service:2024sl70021-${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Code Inspection') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    withCredentials([
                        string(
                            credentialsId: 'sonarqube-token',
                            variable: 'SONAR_TOKEN'
                        )
                    ]) {
                        sh '''
                            mvn -B org.sonarsource.scanner.maven:sonar-maven-plugin:3.11.0.3922:sonar \
                            -Dsonar.projectKey=leave-service-2024sl70021 \
                            -Dsonar.host.url=$SONAR_HOST_URL \
                            -Dsonar.token=$SONAR_TOKEN \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh '''
                    docker rm -f leave-service-staging || true

                    docker run -d \
                    --name leave-service-staging \
                    -p 8085:8080 \
                    -e APP_ENV=staging \
                    $IMAGE_NAME
                '''
            }
        }

        stage('Verify Staging') {
            steps {
                sh '''
                    sleep 5
                    curl -f http://localhost:8085
                '''
            }
        }

        stage('Approval') {
            steps {
                input message: 'Approve deployment to Production?',
                      ok: 'Approve'
            }
        }

        stage('Deploy to Production') {
            steps {
                sh '''
                    docker rm -f leave-service-production || true

                    docker run -d \
                    --name leave-service-production \
                    -p 8086:8080 \
                    -e APP_ENV=production \
                    $IMAGE_NAME
                '''
            }
        }

        stage('Verify Production') {
            steps {
                sh '''
                    sleep 5
                    curl -f http://localhost:8086
                '''
            }
        }

        stage('Verify Same Docker Image') {
            steps {
                sh '''
                    docker ps --filter name=leave-service-staging \
                    --format "{{.Names}} {{.Image}}"

                    docker ps --filter name=leave-service-production \
                    --format "{{.Names}} {{.Image}}"
                '''
            }
        }
    }
}

