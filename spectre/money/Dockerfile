FROM java:8-jre

ADD ./target/money.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/money.jar"]

EXPOSE 11111
