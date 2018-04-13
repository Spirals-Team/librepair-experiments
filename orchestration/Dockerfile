FROM java:8-jre

ADD ./target/orchestration-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/orchestration-service.jar"]

EXPOSE 1111