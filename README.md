# call-center

[![Build Status](https://travis-ci.org/jkevingutierrez/call-center.svg?branch=master)](https://travis-ci.org/jkevingutierrez/call-center)

An API for manage a multithreaded call center using Spring Boot.

You can check the endpoints on [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) locally or [https://call-center-almundo.herokuapp.com/swagger-ui.html](https://call-center-almundo.herokuapp.com/swagger-ui.html)

To create a call and dispatch it, you can use the endpoint [http://localhost:8080/dispatch](http://localhost:8080/dispatch) locally or [https://call-center-almundo.herokuapp.com/dispatch](https://call-center-almundo.herokuapp.com/dispatch)

To create multiple calls and dispatch all of them, you can use the endpoint [http://localhost:8080/dispatch/{numberOfCalls}](http://localhost:8080/dispatch/) locally or [https://call-center-almundo.herokuapp.com/dispatch/{numberOfCalls}](https://call-center-almundo.herokuapp.com/dispatch/)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* JDK 8
* Postgresql 9.6

### Installing

First build the project with maven

```
mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
```

Then execute the jar file

```
java -jar target/call-center-0.0.1-SNAPSHOT.jar
```

Open [http://localhost:8080](http://localhost:8080)

## Running the tests

```
mvnw test -B
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
