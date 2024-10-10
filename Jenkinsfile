pipeline {
  agent { node { label "maven-sonarqube-node" } }
  
  parameters {
    choice(name: 'image_repo_sha', choices: ['ia1o0c8b5', '4568366404742', '922266408974', '576900672829'], description: 'AWS account image repo sha value')
    choice(name: 'Environment', choices: ['Dev', 'QA', 'UAT', 'Prod'], description: 'Target environment for deployment')
    string(name: 'ecr_tag', defaultValue: '1.0.0', description: 'Assign the ECR tag version for the build')
  }

  tools {
    maven "Maven-3.9.8"
  }

  stages {
    stage('1. Git Checkout') {
      steps {
        git branch: 'release', credentialsId: 'Github-pat', url: 'https://github.com/ndiforfusi/addressbook.git'
      }
    }
    stage('2. Build with Maven') { 
      steps {
        sh "mvn clean package"
      }
    }
    stage('3. SonarQube Analysis') {
          environment {
                scannerHome = tool 'SonarQube-Scanner-6.2.1'
            }
            steps {
              withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                      sh """
                      ${scannerHome}/bin/sonar-scanner  \
                      -Dsonar.projectKey=addressbook-application \
                      -Dsonar.projectName='addressbook-application' \
                      -Dsonar.host.url=https://sonarqube.dominionsystem.org \
                      -Dsonar.token=${SONAR_TOKEN} \
                      -Dsonar.sources=src/main/java/ \
                      -Dsonar.java.binaries=target/classes \
                     """
                  }
              }
        }
    stage('4. Docker Image Build') {
      steps {
        sh "aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/${image_repo_sha}"
        sh "sudo docker build -t addressbook ."
        sh "docker tag addressbook:latest public.ecr.aws/${image_repo_sha}/addressbook:${params.ecr_tag}"
        sh "docker push public.ecr.aws/${image_repo_sha}/addressbook:${params.ecr_tag}"
      }
    }

    stage('5. Application Deployment in EKS') {
      steps {
        kubeconfig(caCertificate: '', credentialsId: 'k8s-kubeconfig', serverUrl: '') {
          sh "kubectl apply -f manifest"
        }
      }
    }

    stage('6. Monitoring Solution Deployment in EKS') {
      steps {
        kubeconfig(caCertificate: '', credentialsId: 'kubeconfig', serverUrl: '') {
          sh "kubectl apply -f monitoring"
        }
      }
    }

    stage('7. Email Notification') {
      steps {
        mail bcc: 'fusisoft@gmail.com', body: '''Build is Over. Check the application using the URL below:
         https://abook.dominionsystem.com/addressbook-1.0
         Let me know if the changes look okay.
         Thanks,
         Dominion System Technologies,
         +1 (313) 413-1477''', 
         subject: 'Application was Successfully Deployed!!', to: 'fusisoft@gmail.com'
      }
    }
  }
}





