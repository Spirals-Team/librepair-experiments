[![Maven Central](https://img.shields.io/maven-central/v/io.github.bonigarcia/selenium-jupiter.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aio.github.bonigarcia%20a%3Aselenium-jupiter)
[![Build Status](https://travis-ci.org/bonigarcia/selenium-jupiter.svg?branch=master)](https://travis-ci.org/bonigarcia/selenium-jupiter)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=io.github.bonigarcia:selenium-jupiter&metric=alert_status)](https://sonarcloud.io/dashboard/index/io.github.bonigarcia:selenium-jupiter)
[![codecov](https://codecov.io/gh/bonigarcia/selenium-jupiter/branch/master/graph/badge.svg)](https://codecov.io/gh/bonigarcia/selenium-jupiter)
[![badge-jdk](https://img.shields.io/badge/jdk-8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License badge](https://img.shields.io/badge/license-Apache2-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Support badge]( https://img.shields.io/badge/support-sof-green.svg)](http://stackoverflow.com/questions/tagged/selenium-jupiter)
[![Twitter](https://img.shields.io/badge/follow-@boni_gg-green.svg)](https://twitter.com/boni_gg)

# Selenium-Jupiter [![][Logo]][GitHub Repository]

*Selenium-Jupiter* is a JUnit 5 extension aimed to ease the use of Selenium (WebDriver and Grid) in JUnit 5 tests. This library is open source, released under the terms of [Apache 2.0 License].

## Basic usage

In order to include *Selenium-Jupiter* in a Maven project, first add the following dependency to your `pom.xml` (Java 8 required):

```xml
<dependency>
	<groupId>io.github.bonigarcia</groupId>
	<artifactId>selenium-jupiter</artifactId>
	<version>2.1.1</version>
</dependency>
```

*Selenium-Jupiter* is typically used by tests. In that case, the scope of the dependency should be test (`<scope>test</scope>`).

Once we have included this dependency, *Selenium-Jupiter* manages the WebDriver instances and inject them as parameters in your JUnit 5 tests:

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class SeleniumJupiterTest {

    @Test
    public void testChrome(ChromeDriver driver) {
    	// use chrome in this test
    }

    @Test
    public void testFirefox(FirefoxDriver driver) {
    	// use firefox in this test
    }

}
```

Internally, *Selenium-Jupiter* uses [WebDriverManager] to manage the WebDriver binaries (i.e. *chromedriver*, *geckodriver*,  *operadriver*, and so on) required to use local browsers.

## Docker browsers

As of version 2, *Selenium-Jupiter* allows to use browsers in [Docker] containers. The only requirement is to get installed [Docker Engine] in the machine running the tests. A simple example using this feature is the following:

```java
import static io.github.bonigarcia.BrowserType.CHROME;
import static io.github.bonigarcia.BrowserType.FIREFOX;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.DockerBrowser;
import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class SeleniumJupiterDockerTest {

    @Test
    public void testChrome(
            @DockerBrowser(type = CHROME, version = "latest") RemoteWebDriver driver) {
        // use chrome (latest version) in this test
    }

    @Test
    public void testFirefox(
            @DockerBrowser(type = FIREFOX, version = "57.0") RemoteWebDriver driver) {
        // use firefox (version 57.0) in this test
    }

}
```

## Documentation

You can find more details and examples on the [Selenium-Jupiter user guide].

## About

Selenium-Jupiter (Copyright &copy; 2017-2018) is a project by [Boni Garcia] licensed under [Apache 2.0 License]. Comments, questions and suggestions are always very [welcome][Selenium-Jupiter issues]!

[Apache 2.0 License]: http://www.apache.org/licenses/LICENSE-2.0
[Boni Garcia]: http://bonigarcia.github.io/
[Docker]: https://www.docker.com/
[Docker Engine]: https://www.docker.com/get-docker
[GitHub Repository]: https://github.com/bonigarcia/selenium-jupiter
[Logo]: http://bonigarcia.github.io/img/selenium-jupiter.png
[Selenium-Jupiter user guide]: https://bonigarcia.github.io/selenium-jupiter/
[Selenium-Jupiter issues]: https://github.com/bonigarcia/selenium-jupiter/issues
[Selenium Webdriver]: http://docs.seleniumhq.org/projects/webdriver/
[WebDriverManager]: https://github.com/bonigarcia/webdrivermanager
