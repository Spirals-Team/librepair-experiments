# dockerfile
FROM java:8

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify", "-q"]

#ADD ShopOfHanSQL /code/ShopOfHanSQL
ADD src /code/src
RUN ["mvn", "package", "-q"]

EXPOSE 8081
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/App-jar-with-dependencies.jar"]
