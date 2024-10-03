pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout code from your repository
                git 'https://github.com/aliisaro/QuadLingo-Group-project.git'
            }
        }

        stage('Build') {
            steps {
                // Build the project
                bat 'mvn clean package'
            }
        }

        stage('Run Unit Tests') {
            steps {
                // Run the unit tests
                bat 'mvn test'
            }
            post {
                always {
                    // Capture the test results
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage Report') {
            steps {
                // Generate the JaCoCo coverage report
                bat 'mvn jacoco:report'
            }
            post {
                always {
                    // Publish the JaCoCo coverage report
                    jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
    }
}
