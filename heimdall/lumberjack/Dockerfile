FROM java:8-jre

ADD ./target/lumberjack.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/lumberjack.jar"]

EXPOSE 11235