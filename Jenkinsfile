
pipeline {
    agent { docker { image 'maven:3.6.0-jdk-11' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn deploy'
            }
        }
    }
}