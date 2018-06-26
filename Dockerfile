FROM maven:3.5-jdk-8-alpine
RUN ls -l
WORKDIR /usr/src/Inci_e5a
COPY . /usr/src/Inci_e5a/
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8090
CMD ["java", "-jar", "target/Inci_e5a-0.1.1.jar", "--agents=http://agents:8080/agent"]