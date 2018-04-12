//pipeline {
//    agent any
//    stages {
//        stage('Build') {
//            steps {
//                sh './gradlew build'
//            }
//        }
//        stage('Test') {
//            steps {
//                sh './gradlew check'
//            }
//        }
//    }
//
//    post {
//        always {
//            archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true
//            junit 'build/reports/**/*.xml'
//        }
//    }
//}
node {

   
        stage ('Build'){
            sh 'mvn -Dmaven.test.failure.ignore clean package'                    
        }

        stage('Results') {
            junit '**/target/surefire-reports/TEST-*.xml'
            archive 'target/*.jar'
        }

}
 
//   stage('Build') {
//      // Run the maven build
//      sh "mvn -Dmaven.test.failure.ignore clean package"
//   }
//   stage('Results') {
//      junit '**/target/surefire-reports/TEST-*.xml'
//      archive 'target/*.jar'
//   }
//}
