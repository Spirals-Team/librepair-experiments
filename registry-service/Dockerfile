FROM java:8-jre
MAINTAINER Habeeb Odunsi <habeebodunsi@gmail.com>

ADD ./target/registry-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/registry-service.jar"]

EXPOSE 8761