FROM java:8-jre

ADD ./target/auth-service.jar /app/
VOLUME /ubuntu1
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service.jar"]

EXPOSE 5000