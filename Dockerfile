FROM openjdk:8-jre-alpine
RUN mkdir -p /var/lib/web-jobs
COPY ./target/WebJobs-0.0.1-SNAPSHOT.jar /var/lib/web-jobs
WORKDIR /var/lib/web-jobs
EXPOSE 8888
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "WebJobs-0.0.1-SNAPSHOT.jar", "--server.port=8888"]
#ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n", "-jar", "WebJobs-0.0.1-SNAPSHOT.jar", "--server.port=8888"]