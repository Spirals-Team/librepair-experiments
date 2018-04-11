FROM java:8-jre

ADD ./target/money-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/money-service.jar"]

EXPOSE 11111
