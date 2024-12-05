pipeline {
    agent any
    //TÄHÄN PITÄÄ LAITTAA OMAT CREDENTIALS JA DOCKERHUB REPO
    environment {
        // Define Docker Hub credentials ID
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub_credential'
        // Define Docker Hub repository name
        DOCKERHUB_REPO = 'aliisar/quadlingo'
        // Define Docker image tag
        DOCKER_IMAGE_TAG = 'latest'
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/aliisaro/QuadLingo-Group-project.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
            post {
                always {
                    echo 'Cleaning up build files...'
                }
            }
        }
        stage('Run Unit Tests') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'  // Capture test reports
                }
            }
        }
        stage('Code Coverage Report') {
            steps {
                bat 'mvn jacoco:report'
            }
            post {
                always {
                    jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
    }
}
