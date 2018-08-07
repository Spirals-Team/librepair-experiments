FROM java:8-jre

ADD ./target/auth.jar /app/
VOLUME /ubuntu1
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8004,suspend=n", "-Xmx200m", "-jar", "/app/auth.jar"]

EXPOSE 5000 8004