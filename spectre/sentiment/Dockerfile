FROM java:8-jre

ADD ./target/sentiment-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/sentiment-service.jar"]

EXPOSE 1234
