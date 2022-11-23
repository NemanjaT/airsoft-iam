FROM openjdk:17-jdk-alpine
MAINTAINER Nemanja Tozic <nemanjat94@gmail.com>
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
