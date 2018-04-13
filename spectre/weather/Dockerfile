FROM java:8-jre

ADD ./target/weather-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/weather-service.jar"]

EXPOSE 9999