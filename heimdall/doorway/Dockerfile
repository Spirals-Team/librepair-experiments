FROM java:8-jre

ADD ./target/doorway.jar /app/
VOLUME /ubuntu1
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n", "-Xmx200m", "-jar", "/app/doorway.jar"]

# remote debugging port 
EXPOSE 3333 8000
