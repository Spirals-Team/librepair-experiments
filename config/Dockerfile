FROM java:8-jre

ADD ./target/config-service.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/config-service.jar"]

EXPOSE 8888