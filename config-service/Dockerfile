FROM java:8-jre
MAINTAINER Habeeb Odunsi <habeebodunsi@gmail.com>

ADD ./target/config-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-service.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8100/health || exit 1

EXPOSE 8100