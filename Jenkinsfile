pipeline{

  agent any

  tools {
          maven 'Apache Maven'
      }
  environment {
          COMMIT_HASH = "${sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()}"
          IMAGE_NAME = "RestaurantService"
      }

  stages{

      stage("testing"){
        steps{
             sh 'mvn test'
        }
        post{
            always{
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }
      }
//       stage('Code Analysis: Sonarqube') {
//                   steps {
//                       withSonarQubeEnv('SonarQube') {
//                           sh 'mvn sonar:sonar'
//                       }
//                   }
//               }
//       stage('Await Quality Gateway') {
//            steps {
//                waitForQualityGate abortPipeline: true
//                }
//       }
      stage("package"){
            steps{
                sh 'mvn clean package'
            }
      }
      stage("Docker Build") {
          steps {
              echo "Docker Build...."
//               sh "aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin ${AWS_ID}.dkr.ecr.us-east-2.amazonaws.com"
              sh "docker build --tag ${IMG_NAME}:${COMMIT_HASH} ."
//               sh "docker tag ${IMG_NAME}:${COMMIT_HASH} ${AWS_ID}.dkr.ecr.us-east-2.amazonaws.com/${IMG_NAME}:${COMMIT_HASH}"
              echo "Docker Push..."
//               sh "docker push ${AWS_ID}.dkr.ecr.us-east-2.amazonaws.com/${IMG_NAME}:${COMMIT_HASH}"
          }
      }
  }
  post {
          always {
              sh 'mvn clean'
              sh "docker system prune -f"
          }
      }

}
