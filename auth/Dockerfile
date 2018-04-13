FROM java:8-jre

ADD ./target/auth-service.jar /app/
VOLUME /ubuntu1
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8002,suspend=n", "-Xmx200m", "-jar", "/app/auth-service.jar"]

EXPOSE 5000 8002