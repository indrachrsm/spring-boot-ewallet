FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
ARG JAR_FILE=target/ewallet-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
