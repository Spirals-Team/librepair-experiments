FROM java:8-jre

ADD ./target/web-service.jar /app/
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n", "-Xmx200m", "-jar", "/app/web-service.jar"]

# remote debugging port 
EXPOSE 3333 8000
