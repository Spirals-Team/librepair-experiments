FROM java:8-jre

ADD ./target/greeter.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/greeter.jar"]

EXPOSE 1123