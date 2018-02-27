FROM java:8-jre
MAINTAINER Habeeb Odunsi <habeebodunsi@gmail.com>

ADD ./target/user-authentication-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/user-authentication-service.jar"]

EXPOSE 5000