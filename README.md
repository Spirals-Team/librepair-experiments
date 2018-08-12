# sylenium :busts_in_silhouette:

### Build information:

[![Build Status](https://travis-ci.org/symonk/sylenium.svg?branch=master)](https://travis-ci.org/symonk/sylenium)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/symonk/sylenium/blob/master/LICENSE)
![Free](https://img.shields.io/badge/free-open--source-green.svg)
[![Sonar Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=io.github.symonk:sylenium&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.symonk:sylenium)
![Sonar Maintainability](https://sonarcloud.io/api/project_badges/measure?project=io.github.symonk:sylenium&metric=sqale_rating)
[![Sonar Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.symonk:sylenium&metric=coverage)](https://sonarcloud.io/dashboard?id=io.github.symonk:sylenium)

### Contact me:

[![Linked In](https://img.shields.io/badge/Add%20Me%20On-LinkedIn-orange.svg)](https://www.linkedin.com/in/simonk09/)
[![Linked In](https://img.shields.io/badge/Join%20Me%20On-Slack-orange.svg)](https://testersio.slack.com)

We aim to provide our end users with a fluent high level api for dealing with selenium.  We also have a solid aim to maintain an extremely high level of coverage at both 
unit and integration layers within our test framework and respond to end user requests and contributions as quickly as possible.

## What is Sylenium? (or what will it be rather!)  :octocat:

Sylenium is a high level wrapper around selenium with a ton more features!  It provides a concise api for end users to utilise within their selenium tests without the headache and complexity of traditional test automation frameworks.
We care about the results, being a contributor to allure2, it was only natural that I included the beautiful reporting right out of the box. Whether you are a entry level automation engineer trying to get impactful quickly or a seasoned pro who 
no longer wants to maintain their own wrapper, Sylenium is the tool for you.  Import us and get straight down to the test business layer.

See a simple test below, no driver management involved! `load();` does all the hard work for you as per your runtime variables.

```java
@Test
@Issue("Issue-001")
@TmsLink("TestCase-002")
public void syleniumTestMethod() {
 load("https://www.google.co.uk");
 $id("elementId").put("value");
 $css("#form").submit();
 $id("user").containsText("Sylenium rocks!");
} 
```

## The power of Page Objects with sylenium

No driver code? no initiation of elements? clean proxied page factory approach:

```java
@PageUrl(type = "absolute", url = "https://www.bing.com")
public class BingPage {

  @FindBy(id = "sb_form_q")
  private SyElement searchBox;

  @Step("Execute a search")
  public void search(final String term) { 
      searchBox.
            erase().
            appendText(term).
            pushEnter();
    }
}
```

## Wave goodbye to raw selenium headaches :monkey:

- Problems with stale elements? sylenium will handle those for you.
- Problems with ajax requests? sylenium will handle those for you.
- Problems with angular requests? sylenium can be enabled to handle those for you. (experimental)
- Problems with concurrent driver management? sylenium will handle that for you. 
- Problems with test distribution? sylenium provides powerful remote capabilities using a simple config setting.

## Reporting? no problem :fire:

![Sylenium Reporting](https://raw.githubusercontent.com/symonk/sylenium/master/src/main/resources/images/sylenium_reports.png)

### Release History:

- 0.1 beta

```java
<dependency>
    <groupId>io.symonk.sylenium</groupId>
    <artifactId>sylenium</artifactId>
    <version>placeholder</version>
</dependency>
```

### Backers
Many thanks to the following backers who support the project in various ways:

[![Intellij IDEA](https://cloud.google.com/tools/images/icon_IntelliJIDEA.png)](http://www.jetbrains.com/idea)

## Authors
Sylenium has been designed, developed and maintained since July 2018 by [Sy](https://github.com/symonk) (SymonK).
