FROM java:8-jre

ADD ./target/job-service.jar /app/
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8003,suspend=n", "-Xmx200m", "-jar", "/app/job-service.jar"]

EXPOSE 8765 8003
