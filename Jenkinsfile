pipeline{

  agent any
  tools {
          maven 'Maven 3.3.9'
      }

  stages{

      stage("checkout"){
        steps{
          git branch: 'tests-and-small-mods', url: 'https://github.com/Crumbs-SS/RestaurantService'
          }
      }
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
