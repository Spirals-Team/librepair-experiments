
# java-coaching
Design Patterns implemented in Java

[Coaching](coaching.html)

## Java Patterns

	<dependency>
		<groupId>spamer.me.uk</groupId>
		<artifactId>java-coaching</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<packaging>jar</packaging>
	</dependency>
	
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ff7e76d6a4924d5da8d9f1c1cc7fb035)](https://www.codacy.com/app/Martin-Spamer/java-coaching?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Martin-Spamer/java-coaching&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Martin-Spamer/java-coaching.svg?branch=master)](https://travis-ci.org/Martin-Spamer/java-coaching)


 ||maven-surefire-plugin|maven-failsafe-plugin|
 |Main purpose|unit tests|integration tests
 |Build phase|test|	
 ||test|verify|

 |wildcard pattern|	
 **/Test*.java
 **/*Test.java
 **/*TestCase.java
 |
 **/IT*.java
 **/*IT.java
 **/*ITCase.java
 |
 |Output|${basedir}/target/surefire-reports|${basedir}/target/failsafe-reports|
