pipeline
{
     agent any

  environment
  {
          COMMIT_HASH = "${sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()}"
          IMG_NAME = "restaurantservice"
          AWS_ID = "728482858339"
          DB_CRED = credentials('prod/crumbs/db/cred')
//           DB_ENGINE = credentials('engine')
//           DB_HOST = credentials('host')
//           DB_PORT = credentials('port')
//           DB_NAME = credentials('dbname')
//           USERNAME = credentials('username')
//           PASSWORD = credentials('password')
  }
  tools
  {
            maven 'maven'
            jdk 'java'
  }
  stages
  {
        stage("test")
        {
            steps
            {
                withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'jenkins_credentials', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')])
                {
                  sh 'echo aws secretsmanager get-secret-value --secret-id arn:aws:secretsmanager:us-east-1:728482858339:secret:prod/crumbs/db/cred-584hNX'
                  sh 'echo ${DB_CRED}'
//                  sh 'echo ${DB_ENGINE}'

                }
            }
         }
  }

//   stages
//   {
//        stage("Build")
//        {
//             steps {
//                  sh 'mvn clean install'
//             }
//        }
//
//        stage("Test")
//        {
//                 steps
//                 {
//                     sh 'mvn test'
//                     junit '**/target/surefire-reports/*.xml'
//                 }
//        }
//
//        stage('Code Analysis: Sonarqube')
//        {
//                    steps {
//                        withSonarQubeEnv('sonarqube') {
//                            sh 'mvn sonar:sonar'
//                        }
//                    }
//        }
//        stage('Await Quality Gateway')
//        {
//             steps {
//                 waitForQualityGate abortPipeline: true
//             }
//        }
//       stage("Package")
//       {
//             steps
//             {
//                 sh 'mvn clean package'
//             }
//       }
//       stage("Docker Build") {
//
//           steps {
//               echo "Docker Build...."
//               withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'jenkins_credentials', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
//                         sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${AWS_ID}.dkr.ecr.us-east-1.amazonaws.com"
//               }
//               sh "docker build --tag ${IMG_NAME}:${COMMIT_HASH} ."
//                sh "docker tag ${IMG_NAME}:${COMMIT_HASH} ${AWS_ID}.dkr.ecr.us-east-1.amazonaws.com/${IMG_NAME}:${COMMIT_HASH}"
//               echo "Docker Push..."
//                sh "docker push ${AWS_ID}.dkr.ecr.us-east-1.amazonaws.com/${IMG_NAME}:${COMMIT_HASH}"
//           }
//       }
//   }
//   post
//   {
//           always
//           {
//               sh 'mvn clean'
//               sh "docker system prune -f"
//           }
//   }

}
