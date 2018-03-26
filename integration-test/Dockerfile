FROM java:8-jre

ADD ./target/integration-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/integration-service.jar"]

EXPOSE 1234