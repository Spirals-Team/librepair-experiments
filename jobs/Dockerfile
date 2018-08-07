FROM java:8-jre

ADD ./target/jobs.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/jobs.jar"]

EXPOSE 8765
