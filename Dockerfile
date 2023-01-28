FROM openjdk:11-alpine
MAINTAINER Gerasimov Mikhail
COPY target/pastabox-0.0.1-SNAPSHOT.jar pastabox.jar
ENTRYPOINT ["java", "-jar", "/pastabox.jar"]

