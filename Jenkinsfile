//dummy change
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

    stage("Sonar code analysis"){
        withCredentials([string(credentialsId: 'sonar-token', variable: 'TOKEN')]) {
            sh " mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$TOKEN"
        }
    }

    stage ("Docker push"){
        sh "docker-compose build"
        sh "docker push veyis/rac"
        sh "docker push veyis/veyisturgut_spring"
        sh "docker system prune -f"
    }
    stage("Run"){
        withCredentials([string(credentialsId: 'github-token', variable: 'TOKEN')]) {
                sh 'ssh -tt -o "StrictHostKeyChecking=no" -i /var/lib/jenkins/nodepair.pem ec2-user@ec2-18-119-162-119.us-east-2.compute.amazonaws.com " git clone https://veyisTurgut:$TOKEN@github.com/veyisTurgut/Obss-internship-project.git; cd Obss-internship-project; docker pull veyis/veyisturgut_spring; docker pull veyis/rac; docker start postgresy; docker exec -t postgresy pg_dumpall -c -U postgres > backup_dump.sql; docker-compose up -d; cat backup_dump.sql | docker exec -i postgresy psql -U postgres"'
        }
    }
}
