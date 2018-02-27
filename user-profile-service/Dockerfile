FROM java:8-jre
MAINTAINER Habeeb Odunsi <habeebodunsi@gmail.com>

ADD ./target/user-profile-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/user-profile-service.jar"]

EXPOSE 6000