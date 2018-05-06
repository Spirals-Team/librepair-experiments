FROM maven:3.5-jdk-8-alpine
WORKDIR /usr/src/InciDashboardi3b
COPY . /usr/src/InciDashboardi3b/
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8090
CMD ["java", "-jar", "target/InciDashboard_i3b-0.0.1.jar", "--spring.kafka.bootstrap-servers=kafka:9092", "--spring.data.mongodb.host=mongo"]