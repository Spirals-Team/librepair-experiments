FROM java:8-jre

ADD ./target/orchestration.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/orchestration.jar"]

EXPOSE 1111