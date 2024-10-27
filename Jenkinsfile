pipeline {
    agent { node { label "Maven-Sonarqube-Server" } }   
    parameters {
      choice(name: 'aws_account',choices: ['058264384488', '4568366404742', '922266408974','576900672829'], description: 'aws account hosting image registry')
      choice(name: 'Environment', choices: ['Dev', 'QA', 'UAT', 'Prod'], description: 'Target environment for deployment')
      string(name: 'ecr_tag', defaultValue: '1.7.0', description: 'Assign the ECR tag version for the build')
    }

    tools {
      maven "Maven-3.9.8"
    }

    stages {
    stage('1. Git Checkout') {
      steps {
        git branch: 'release', credentialsId: 'Github-pat', url: 'https://github.com/pamyleitich/addressbook.git'
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
                      -Dsonar.host.url=http://34.213.29.188:9000 \
                      -Dsonar.token=${SONAR_TOKEN} \
                      -Dsonar.sources=src/main/java/ \
                      -Dsonar.java.binaries=target/classes \
                     """
                  }
              }
        }
    stage('4. Docker Image Build') {
      steps {
          sh "aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/e4o4k3j4"
          sh "sudo docker build -t addressbook ."
          sh "sudo docker tag addressbook:latest public.ecr.aws/e4o4k3j4/addressbook:latest${params.ecr_tag}"
          sh "sudo docker push public.ecr.aws/e4o4k3j4/addressbook:${params.ecr_tag}"
        
      }
    }

   
    stage('5. Application Deployment in EKS') {
      steps {
        kubeconfig(caCertificate: '', credentialsId: 'kubeconfig', serverUrl: '') {
          sh "kubectl apply -f manifest"
        }
      }
    }

    stage('6. Monitoring Solution Deployment in EKS') {
      steps {
        kubeconfig(caCertificate: '', credentialsId: 'kubeconfig', serverUrl: '') {
          sh "kubectl apply -k monitoring"
          sh("""script/install_helm.sh""") 
          sh("""script/install_prometheus.sh""") 
        }
      }
    }

    stage('7. Email Notification') {
      steps {
        mail bcc: 'pamyleitich@gmail.com', body: '''Build is Over. Check the application using the URL below:
         https://app.dominionsystem.org/addressbook-1.0
         Let me know if the changes look okay.
         Thanks,
         Dominion System Technologies,
         +1 (313) 413-1477''', 
         subject: 'Application was Successfully Deployed!!', to: 'fusisoft@gmail.com'
      }
    }
  }
}





