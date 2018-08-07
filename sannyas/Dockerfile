FROM java:8-jre

ADD ./target/sannyas.jar /app/
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8002,suspend=n", "-Xmx512m", "-jar", "/app/sannyas.jar"]

EXPOSE 2222 8002