pipeline {
    agent any
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
