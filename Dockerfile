FROM openjdk:8u121-jre-alpine
ADD target/vitrine.jar vitrine.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /vitrine.jar"]

