FROM java:8-jre

ADD ./target/config.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/config.jar"]

EXPOSE 8888