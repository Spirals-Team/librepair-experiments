pipeline{
    agent any
    stages{
        stage('junit'){
            steps{
                bat 'mvn test'
            }
        }
        stage('integration'){
            steps{
                bat 'mvn verify'
            }
        }
        stage('cobertura'){
            steps{
                bat 'mvn cobertura:cobertura'
            }
        }
    }
    post {
        always {
            junit 'target/**/*.xml'
        }
    }
} 