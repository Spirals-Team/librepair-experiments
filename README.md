# testrex.io

Spring Boot Server 

[![Build Status](https://travis-ci.org/sbunciak/testrex.io.svg?branch=master)](https://travis-ci.org/sbunciak/testrex.io)

## How to run

You can run the application using mvn spring-boot:run, or you can build the JAR file with 

```
mvn clean package
```

Then you can run the JAR file:

```
java -jar target/testrex-server-*.jar
```

You can change the default port value in ``application.properties`` in ``src/main/resources`` folder.
