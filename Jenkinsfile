pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
    }

    stages {
        stage('Build Artifact - Maven') {
            steps {
                sh "mvn clean package -DskipTests=true"
                archiveArtifacts "target/*.jar"
            }
        }

        stage('Unit Tests - JUnit and Jacoco') {
            steps {
                sh "mvn test"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }

        stage('PRINT ENV') {
            steps {
                sh 'printenv'
            }
        }

        stage('Login Docker') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }

        stage('Docker Build and Push') {
            steps {
                withDockerRegistry([credentialsId: "docker-hub", url:""]) {
                    sh 'printenv'
                    sh 'docker build -t erickcernarequejo/libros-app:""$GIT_COMMIT"" .'
                    sh 'docker push erickcernarequejo/libros-app:""$GIT_COMMIT""'
                }
            }
        }
    }
}
