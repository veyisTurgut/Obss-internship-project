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

            //        step([$class: 'CheckStylePublisher',
            //         canRunOnFailed: true,
            //         defaultEncoding: '',
            //        healthy: '100',
            //          pattern: '**/target/checkstyle-result.xml',
            //        unHealthy: '90',
            //        useStableBuildAsReference: true
            //      ])
                }
            }
        }

        stage("Tests and Deployment") {
            //parallel 'Unit tests': {
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
            //}, 'Integration tests': {
            //    stage("Runing integration tests") {
            //        try {
            //           sh "./mvnw test -Pintegration"
            //        } catch(err) {
            //            step([$class: 'JUnitResultArchiver', testResults:
            //              '**/target/surefire-reports/TEST-'
            //                + '*IntegrationTest.xml'])
            //            throw err
            //        }
            //        step([$class: 'JUnitResultArchiver', testResults:
            //          '**/target/surefire-reports/TEST-'
            //            + '*IntegrationTest.xml'])
            //    }
            //}
            /*
            stage("Staging") {
                sh '''pid=\$(lsof -i:8081 -t);
                    kill -TERM \$pid || kill -KILL \$pid'''
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'nohup ./mvnw spring-boot:run -Dserver.port=8081 &'
                }
            }*/
        }
        
        stage("Resolve spring dependency"){
            sh "mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)"
        }
        
        stage("Run"){
            sh "docker-compose up -d"
        }
        
        stage("get inside db"){
            sh "docker exec -i -u 0 postgresy bash"
            stage("deneme"){
                sh "echo oluyo iste"
            }
            stage("insertions"){
                sh "psql -U postgres -d Obsstaj -a -f  /tmp/Obsstaj_public_admin.sql && psql -U postgres -d Obsstaj -a -f  /tmp/Obsstaj_public_subject.sql && psql -U postgres -d Obsstaj -a -f  /tmp/Obsstaj_public_user.sql && psql -U postgres -d Obsstaj -a -f  /tmp/Obsstaj_public_program.sql && psql -U postgres -d Obsstaj -a -f  /tmp/Obsstaj_public_public_mentorship_application.sql &&psql -U postgres -d Obsstaj -a -f  /tmp/Obsstaj_public_phase.sql "
            }
        }
}