pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
        IMAGE='libros-app'
        REGISTRY='erickcernarequejo'
        VERSION='v1'
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

//         stage('Login Docker') {
//             steps {
//                 sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
//             }
//         }

        stage('Docker Build and Push') {
            steps {
                withDockerRegistry([credentialsId: "docker-hub", url:""]) {
                    sh 'printenv'
                    sh 'crictl build -t $DOCKERHUB_CREDENTIALS_USR/$IMAGE:""$GIT_COMMIT"" .'
                    sh 'crictl push $DOCKERHUB_CREDENTIALS_USR/$IMAGE:""$GIT_COMMIT""'
                }
            }
        }

        stage('Kubernetes Deployment - DEV') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh "sed -i 's#replace#$DOCKERHUB_CREDENTIALS_USR/$IMAGE:${GIT_COMMIT}#g' k8s_deployment_service.yaml"
                    sh "kubectl apply -f k8s_deployment_service"
                }
            }
        }
    }
}
