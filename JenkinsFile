pipeline{
    agent any
    stages{
        stage("build"){
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
        stage("test"){
            steps {
                sh 'mvn test'
            }
        }
    }
}
