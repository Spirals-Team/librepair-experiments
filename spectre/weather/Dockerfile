FROM java:8-jre

ADD ./target/weather.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/weather.jar"]

EXPOSE 9999