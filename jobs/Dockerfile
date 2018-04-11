FROM java:8-jre

ADD ./target/job-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/job-service.jar"]

EXPOSE 8765
