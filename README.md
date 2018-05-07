# selenium-maven-allure-example 
[![ReviewNinja](https://app.review.ninja/53761193/badge)](https://app.review.ninja/jzeisweiss/selenium-maven-allure-example)
[![Build Status](https://travis-ci.org/jzeisweiss/selenium-maven-allure-example.svg?branch=develop)](https://travis-ci.org/jzeisweiss/selenium-maven-allure-example)
[![Build Status](https://travis-ci.org/jzeisweiss/selenium-maven-allure-example.svg?branch=master)](https://travis-ci.org/jzeisweiss/selenium-maven-allure-example)

### Table of Contents  
1. [Report Preview](#report-preview)  
2. [Code Samples](#code-samples)  
3. [System Requirements](#system-requirements)  
4. [Eclipse Setup](#eclipse-setup)  
5. [Run The Demo Example](#run-the-demo-example)  
6. [Library References](#library-references)  

---

### Report Preview

![Allure Report Demo](allure-tools/allure_demo.gif?raw=true "Allure Report Demo")

[Report screenshot](allure-tools/allure_report_details.png?raw=true)

---

### Code Samples
```java
@Features("Jimmy's feature")
public class ExampleTest {
	@Stories("Jimmy's full example")
	@Test(description = "Combine all examples.")
	public void testFullExample() {
		new CoreWebDiver().addStep("Produce amazing things")
		                  .recordScreenshot("During Test example.")
		                  .recordDataFile("During test data file", "{ More Data }");
	}
}
```
[Full code sample.](src/test/java/com/bdh/automation/ExampleTest.java)

---

### System Requirements
1. Java 8 
	- *This could technically be down graded to Java 7 but would require some adjustments*
2. Maven

---

### Eclipse Setup
1. Import as an existing Maven project.
2. Install the TestNG Plugin.
3. Optional: If you want to run tests from within Eclipse and want the annotation features to work then you need to add a java agent for AspectJ Weaver to your JVM so that it can watch for the annotations.
	1. Go to Eclipse's `Preferences`
	2. Expand the `Java` section
	3. Click `Installed JREs`
	4. Click on the version of Java 8 that you are using
	5. Click `Edit`
	6. Add `-javaagent:allure-tools/aspectjweaver-1.8.3.jar` to your Default JVM arguments
	7. Click `Finish`
	8. Click `Apply`

---

### Run The Demo Example
- From command line: `$ mvn clean test`
- From Eclipse: 
	1. Navigate to the test file `ExampleTest.java`
	2. Right click on the file 
	3. Click `Run As`
	4. Click `TestNG Test`
	
---

### Library References

| Library       | About |
| ------------- | ------|
| [Selenium](http://www.seleniumhq.org/)     | UI Browser Automation     |
| [Allure](http://allure.qatools.ru/)        | Fancy Test Result Reports |
| [TestNG](http://testng.org/doc/index.html) | Test Suite Management     |


