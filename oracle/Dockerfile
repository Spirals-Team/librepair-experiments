FROM java:8-jre

ADD ./target/oracle.jar /app/
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8001,suspend=n", "-Xmx200m", "-jar", "/app/oracle.jar"]

EXPOSE 4444 8001