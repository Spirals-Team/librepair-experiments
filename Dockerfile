FROM openjdk:8-jdk-alpine

ADD target/project-spring-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]