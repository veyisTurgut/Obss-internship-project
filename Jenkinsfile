node {
    stage ('Clone the project'){
        git branch: "main", credentialsId: "github", url: 'https://github.com/veyisTurgut/Obss-internship-project.git'
        withMaven(maven: "maven 3.8.2") {
            //sh "mvn clean verify"
            sh "mvn -N io.takari:maven:wrapper"

        }
    }
         stage("Compilation and Analysis") {
            parallel 'Compilation': {
                sh "./mvnw clean install -DskipTests"
            }, 'Static Analysis': {
                stage("Checkstyle") {
                    sh "./mvnw checkstyle:checkstyle"
                }
            }
        }

        stage("Tests and Deployment") {
                stage("Runing unit tests") {
                    try {           sh "./mvnw test -Punit"
                    } catch(err) {
                        step([$class: 'JUnitResultArchiver', testResults:
                          '**/target/surefire-reports/TEST-*Test.xml'])
                        throw err
                    }
                   step([$class: 'JUnitResultArchiver', testResults:
                     '**/target/surefire-reports/TEST-*Test.xml'])
                }

        }
        
        stage("Resolve spring dependency"){
            sh "mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)"
        }
        
        stage("Run"){
            withCredentials([string(credentialsId: 'github-token', variable: 'TOKEN')]) {
                    sh 'ssh -tt  -o "StrictHostKeyChecking no" -i $JENKINS_HOME/nodepair.pem ec2-user@ec2-18-119-162-119.us-east-2.compute.amazonaws.com " git clone https://veyisTurgut:$TOKEN@github.com/veyisTurgut/Obss-internship-project.git; cd Obss-internship-project; docker-compose up -d"'                    
                    }
        }

}
