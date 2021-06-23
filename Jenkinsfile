pipeline{

  agent any

  tools {
          maven 'Apache Maven'
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
      stage("build"){
        steps{
          sh 'mvn clean package'
        }
      }

      stage("archiving"){
        steps{
          archiveArtifacts artifacts: '**/target/*.jar'
         }
      }

      stage("deploy"){
          steps{
            echo("building")
          }
      }
  }
}
