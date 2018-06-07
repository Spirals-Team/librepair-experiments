# atf4j
An automated test framework for Java

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9689b2cfb86e4d5a934e031decb1f1ac)](https://www.codacy.com/app/Martin-Spamer/atf4j?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=atf4j/atf4j&amp;utm_campaign=Badge_Grade)

## Automation Test Framework for Java

## Maven Dependency

	<dependency>
		<groupId>net.atf4j</groupId>
		<artifactId>atf4j</artifactId>
		<version>0.11.0-SNAPSHOT</version>
	</dependency>

## Maven usage

	mvn test

### Maven usage on local machine

	mvn test -DisLocal=true

### Maven usage on Jenkins 

	mvn test -DisJenkins=true

### Maven usage with Selenium Grid

	mvn test -DisGrid=true
