FROM java:8-jre
MAINTAINER Zhao Yibo <zhaoyibochn@gmail.com>

ADD ./target/turbine.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/turbine.jar"]

EXPOSE 8989