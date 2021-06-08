FROM tomcat:9.0.39
MAINTAINER roman
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/EpamFinalProject-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/project.war
EXPOSE 8080

#ENTRYPOINT ["java", "-server", "-jar", "./EpamFinalProject-1.0-SNAPSHOT.war"]