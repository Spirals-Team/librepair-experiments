FROM maven:3.5-jdk-8-alpine
WORKDIR /usr/src/Agents_i3b
COPY . /usr/src/Agents_i3b/
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8085
CMD ["java", "-jar", "target/Agents_i3b-0.0.1.jar", "--spring.data.mongodb.host=mongo"]