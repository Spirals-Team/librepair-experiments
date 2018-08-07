FROM java:8-jre

ADD ./target/sentiment.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/sentiment.jar"]

EXPOSE 7777
