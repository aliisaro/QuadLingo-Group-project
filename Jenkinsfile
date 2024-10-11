pipeline {
    agent any

    environment {
        // Define Docker Hub credentials ID
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub_credential'
        // Define Docker Hub repository name
        DOCKERHUB_REPO = 'aliisar/quadlingo'
        // Define Docker image tag
        DOCKER_IMAGE_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Git repository
                git 'https://github.com/aliisaro/QuadLingo-Group-project.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package'  // Build the project using Maven
            }
        }
        stage('Run Unit Tests') {
            steps {
                bat 'mvn test'  // Run unit tests using Maven
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'  // Capture test reports
                }
            }
        }
        stage('Code Coverage Report') {
            steps {
                bat 'mvn jacoco:report'  // Generate JaCoCo coverage report
            }
            post {
                always {
                    jacoco execPattern: 'target/jacoco.exec'  // Publish JaCoCo code coverage results
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                // Build Docker image
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                // Push Docker image to Docker Hub
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }
}
