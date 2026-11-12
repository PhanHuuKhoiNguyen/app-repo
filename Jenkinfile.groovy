pipeline {
    agent any

    environment {
        FE_IMAGE = 'phanhuukhoinguyen1205/repairshop-frontend'
        FE_TAG   = "5.0.${BUILD_NUMBER}"
    }

    stages {
        stage('Check Docker') {
            steps { bat 'docker version' }
        }

        stage('Build frontend') {
            steps {
                dir('repair-shop-frontend') {
                    bat 'docker build -t %FE_IMAGE%:%FE_TAG% .'
                }
            }
        }

        stage('Push frontend') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-cred',
                                                  usernameVariable: 'REG_USER',
                                                  passwordVariable: 'REG_PASS')]) {
                    bat 'echo %REG_PASS%| docker login -u %REG_USER% --password-stdin'
                    bat 'docker push %FE_IMAGE%:%FE_TAG%'
                }
            }
        }
    }

    post {
        always { bat 'docker logout' }
    }
}