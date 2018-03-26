FROM java:8-jre

ADD ./target/heimdall-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/heimdall-service.jar"]

EXPOSE 9090