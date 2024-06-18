FROM tomcat:10.1.23-jdk21-temurin-jammy

ADD *.war /usr/local/tomcat/webapps/

ENV SUTTON_DB_HOST_PORT="localhost:3306"

EXPOSE 8080

CMD ["catalina.sh", "run"]
