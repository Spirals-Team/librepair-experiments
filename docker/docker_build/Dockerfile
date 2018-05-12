FROM openjdk:8-jdk-alpine

# 设置时区 亚太/上海
RUN echo "Asia/Shanghai" > /etc/timezone
ENV TZ "Asia/Shanghai"

VOLUME /tmp
ADD *.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8081