FROM java:8-jre

ADD ./target/localtimer.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/localtimer.jar"]

EXPOSE 6666
