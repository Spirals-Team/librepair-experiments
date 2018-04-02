FROM java:8-jre

ADD ./target/localtimer-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/localtimer-service.jar"]

EXPOSE 6666
