FROM java:8-jre

ADD ./target/oracle-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/oracle-service.jar"]

EXPOSE 4444