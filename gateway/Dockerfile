FROM java:8-jre
MAINTAINER Zhao Yibo <zhaoyibochn@gmail.com>

ADD ./target/gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 4000