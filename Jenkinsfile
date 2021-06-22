pipeline{

  agent any

  stages{

      stage("checkout"){
        steps{
          git branch: 'tests-and-small-mods', url: 'https://github.com/Crumbs-SS/RestaurantService'
          }
      }
      stage("testing"){
        steps{
            sh 'make check || true'
            junit '**/target/*.xml'
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
