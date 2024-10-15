FROM tomcat:9.0.37-jdk8

# Install OpenJDK 11
RUN apt-get update -y && apt-get install openjdk-11-jdk -y

# Set JAVA_HOME and update PATH
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Add your WAR file to the Tomcat webapps directory
ADD ./target/addressbook-1.0.war /usr/local/tomcat/webapps/

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]





# docker stop fusisoft-webapps;docker rm fusisoft-webapps;docker rmi fusisoft-webapps:1.1.0
# rm -rf docker-jenkins-build
# mkdir docker-jenkins-build
# cd docker-jenkins-build
# cp /var/lib/jenkins/workspace/fusisoft-maven-project/target/myapps.war .
# touch dockerfile
# cat <<EOT>>dockerfile
# FROM tomcat:9.0.37-jdk8
# ADD myapps.war /usr/local/tomcat/webapps/
# EXPOSE 8080
# CMD "catalina.sh"  "run"
# EOT
# docker build -t fusisoft-webapps:1.1.0 .
# docker run -itd --name=fusisoft-webapps -p 8085:8080 fusisoft-webapps:1.1.0