FROM openjdk:15-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} odc.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/odc.jar"]