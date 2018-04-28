$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/features/stackoverflow/Search.feature");
formatter.feature({
  "name": "Search and tags",
  "description": "",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "name": "Filter by tags",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@web"
    }
  ]
});
formatter.step({
  "name": "I am on the homepage",
  "keyword": "Given "
});
formatter.step({
  "name": "I go to the tags page",
  "keyword": "When "
});
formatter.step({
  "name": "I filter for \"\u003ctag\u003e\"",
  "keyword": "And "
});
formatter.step({
  "name": "I select the tag \"\u003ctag\u003e\"",
  "keyword": "And "
});
formatter.step({
  "name": "I select the first question",
  "keyword": "And "
});
formatter.step({
  "name": "the question is tagged with \"\u003ctag\u003e\"",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "tag"
      ]
    },
    {
      "cells": [
        "selenium"
      ]
    },
    {
      "cells": [
        "appium"
      ]
    }
  ]
});
formatter.scenario({
  "name": "Filter by tags",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@web"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I am on the homepage",
  "keyword": "Given "
});
formatter.match({
  "location": "HomeSteps.homepage()"
});
formatter.result({
  "error_message": "org.openqa.selenium.WebDriverException: unknown error: cannot find Chrome binary\n  (Driver info: chromedriver\u003d2.38.552522 (437e6fbedfa8762dec75e2c5b3ddb86763dc9dcb),platform\u003dLinux 3.10.0-693.21.1.el7.x86_64 x86_64) (WARNING: The server did not provide any stacktrace information)\nCommand duration or timeout: 54 milliseconds\nBuild info: version: \u00273.11.0\u0027, revision: \u0027e59cfb3\u0027, time: \u00272018-03-11T20:26:55.152Z\u0027\nSystem info: host: \u0027spirals-vortex.lille.inria.fr\u0027, ip: \u0027172.17.0.4\u0027, os.name: \u0027Linux\u0027, os.arch: \u0027amd64\u0027, os.version: \u00273.10.0-693.21.1.el7.x86_64\u0027, java.version: \u00271.8.0_121\u0027\nDriver info: driver.version: ChromeDriver\nselenide.url: https://www.stackoverflow.com\nselenide.baseUrl: http://localhost:8080\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)\n\tat org.openqa.selenium.remote.ErrorHandler.createThrowable(ErrorHandler.java:214)\n\tat org.openqa.selenium.remote.ErrorHandler.throwIfResponseFailed(ErrorHandler.java:166)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$new$0(JsonWireProtocolResponse.java:53)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$getResponseFunction$2(JsonWireProtocolResponse.java:91)\n\tat org.openqa.selenium.remote.ProtocolHandshake.lambda$createSession$0(ProtocolHandshake.java:123)\n\tat java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)\n\tat java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:958)\n\tat java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:126)\n\tat java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:498)\n\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)\n\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)\n\tat java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:152)\n\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n\tat java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:464)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:126)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:73)\n\tat org.openqa.selenium.remote.HttpCommandExecutor.execute(HttpCommandExecutor.java:136)\n\tat org.openqa.selenium.remote.service.DriverCommandExecutor.execute(DriverCommandExecutor.java:83)\n\tat org.openqa.selenium.remote.RemoteWebDriver.execute(RemoteWebDriver.java:545)\n\tat org.openqa.selenium.remote.RemoteWebDriver.startSession(RemoteWebDriver.java:209)\n\tat org.openqa.selenium.remote.RemoteWebDriver.\u003cinit\u003e(RemoteWebDriver.java:132)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:181)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:168)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:157)\n\tat com.codeborne.selenide.webdriver.ChromeDriverFactory.create(ChromeDriverFactory.java:27)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.lambda$createWebDriver$0(WebDriverFactory.java:61)\n\tat java.util.Optional.map(Optional.java:215)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.createWebDriver(WebDriverFactory.java:61)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.createDriver(WebDriverThreadLocalContainer.java:231)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.getAndCheckWebDriver(WebDriverThreadLocalContainer.java:118)\n\tat com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver(WebDriverRunner.java:136)\n\tat com.codeborne.selenide.impl.Navigator.navigateToAbsoluteUrl(Navigator.java:68)\n\tat com.codeborne.selenide.impl.Navigator.open(Navigator.java:32)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:95)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:69)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.pages.HomePage.load(HomePage.java:19)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.steps.HomeSteps.homepage(HomeSteps.java:15)\n\tat ✽.I am on the homepage(src/test/resources/features/stackoverflow/Search.feature:5)\n",
  "status": "failed"
});
formatter.step({
  "name": "I go to the tags page",
  "keyword": "When "
});
formatter.match({
  "location": "HomeSteps.goToTags()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I filter for \"selenium\"",
  "keyword": "And "
});
formatter.match({
  "location": "TagSteps.filter(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I select the tag \"selenium\"",
  "keyword": "And "
});
formatter.match({
  "location": "TagSteps.selectTag(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I select the first question",
  "keyword": "And "
});
formatter.match({
  "location": "QuestionSteps.selectFirstQuestion()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "the question is tagged with \"selenium\"",
  "keyword": "Then "
});
formatter.match({
  "location": "QuestionSteps.isQuestionTaggedWith(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "Filter by tags",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@web"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I am on the homepage",
  "keyword": "Given "
});
formatter.match({
  "location": "HomeSteps.homepage()"
});
formatter.result({
  "error_message": "org.openqa.selenium.WebDriverException: unknown error: cannot find Chrome binary\n  (Driver info: chromedriver\u003d2.38.552522 (437e6fbedfa8762dec75e2c5b3ddb86763dc9dcb),platform\u003dLinux 3.10.0-693.21.1.el7.x86_64 x86_64) (WARNING: The server did not provide any stacktrace information)\nCommand duration or timeout: 7 milliseconds\nBuild info: version: \u00273.11.0\u0027, revision: \u0027e59cfb3\u0027, time: \u00272018-03-11T20:26:55.152Z\u0027\nSystem info: host: \u0027spirals-vortex.lille.inria.fr\u0027, ip: \u0027172.17.0.4\u0027, os.name: \u0027Linux\u0027, os.arch: \u0027amd64\u0027, os.version: \u00273.10.0-693.21.1.el7.x86_64\u0027, java.version: \u00271.8.0_121\u0027\nDriver info: driver.version: ChromeDriver\nselenide.url: https://www.stackoverflow.com\nselenide.baseUrl: http://localhost:8080\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)\n\tat org.openqa.selenium.remote.ErrorHandler.createThrowable(ErrorHandler.java:214)\n\tat org.openqa.selenium.remote.ErrorHandler.throwIfResponseFailed(ErrorHandler.java:166)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$new$0(JsonWireProtocolResponse.java:53)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$getResponseFunction$2(JsonWireProtocolResponse.java:91)\n\tat org.openqa.selenium.remote.ProtocolHandshake.lambda$createSession$0(ProtocolHandshake.java:123)\n\tat java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)\n\tat java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:958)\n\tat java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:126)\n\tat java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:498)\n\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)\n\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)\n\tat java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:152)\n\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n\tat java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:464)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:126)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:73)\n\tat org.openqa.selenium.remote.HttpCommandExecutor.execute(HttpCommandExecutor.java:136)\n\tat org.openqa.selenium.remote.service.DriverCommandExecutor.execute(DriverCommandExecutor.java:83)\n\tat org.openqa.selenium.remote.RemoteWebDriver.execute(RemoteWebDriver.java:545)\n\tat org.openqa.selenium.remote.RemoteWebDriver.startSession(RemoteWebDriver.java:209)\n\tat org.openqa.selenium.remote.RemoteWebDriver.\u003cinit\u003e(RemoteWebDriver.java:132)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:181)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:168)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:157)\n\tat com.codeborne.selenide.webdriver.ChromeDriverFactory.create(ChromeDriverFactory.java:27)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.lambda$createWebDriver$0(WebDriverFactory.java:61)\n\tat java.util.Optional.map(Optional.java:215)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.createWebDriver(WebDriverFactory.java:61)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.createDriver(WebDriverThreadLocalContainer.java:231)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.getAndCheckWebDriver(WebDriverThreadLocalContainer.java:118)\n\tat com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver(WebDriverRunner.java:136)\n\tat com.codeborne.selenide.impl.Navigator.navigateToAbsoluteUrl(Navigator.java:68)\n\tat com.codeborne.selenide.impl.Navigator.open(Navigator.java:32)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:95)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:69)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.pages.HomePage.load(HomePage.java:19)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.steps.HomeSteps.homepage(HomeSteps.java:15)\n\tat ✽.I am on the homepage(src/test/resources/features/stackoverflow/Search.feature:5)\n",
  "status": "failed"
});
formatter.step({
  "name": "I go to the tags page",
  "keyword": "When "
});
formatter.match({
  "location": "HomeSteps.goToTags()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I filter for \"appium\"",
  "keyword": "And "
});
formatter.match({
  "location": "TagSteps.filter(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I select the tag \"appium\"",
  "keyword": "And "
});
formatter.match({
  "location": "TagSteps.selectTag(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I select the first question",
  "keyword": "And "
});
formatter.match({
  "location": "QuestionSteps.selectFirstQuestion()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "the question is tagged with \"appium\"",
  "keyword": "Then "
});
formatter.match({
  "location": "QuestionSteps.isQuestionTaggedWith(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "status": "passed"
});
formatter.scenarioOutline({
  "name": "Use the search function",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@web"
    },
    {
      "name": "@android"
    },
    {
      "name": "@ios"
    }
  ]
});
formatter.step({
  "name": "I am on the homepage",
  "keyword": "Given "
});
formatter.step({
  "name": "I search for \"\u003ctag\u003e\"",
  "keyword": "When "
});
formatter.step({
  "name": "I select the first question",
  "keyword": "And "
});
formatter.step({
  "name": "the question is tagged with \"\u003ctag\u003e\"",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "tag"
      ]
    },
    {
      "cells": [
        "selenium"
      ]
    },
    {
      "cells": [
        "appium"
      ]
    }
  ]
});
formatter.scenario({
  "name": "Use the search function",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@web"
    },
    {
      "name": "@android"
    },
    {
      "name": "@ios"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I am on the homepage",
  "keyword": "Given "
});
formatter.match({
  "location": "HomeSteps.homepage()"
});
formatter.result({
  "error_message": "org.openqa.selenium.WebDriverException: unknown error: cannot find Chrome binary\n  (Driver info: chromedriver\u003d2.38.552522 (437e6fbedfa8762dec75e2c5b3ddb86763dc9dcb),platform\u003dLinux 3.10.0-693.21.1.el7.x86_64 x86_64) (WARNING: The server did not provide any stacktrace information)\nCommand duration or timeout: 6 milliseconds\nBuild info: version: \u00273.11.0\u0027, revision: \u0027e59cfb3\u0027, time: \u00272018-03-11T20:26:55.152Z\u0027\nSystem info: host: \u0027spirals-vortex.lille.inria.fr\u0027, ip: \u0027172.17.0.4\u0027, os.name: \u0027Linux\u0027, os.arch: \u0027amd64\u0027, os.version: \u00273.10.0-693.21.1.el7.x86_64\u0027, java.version: \u00271.8.0_121\u0027\nDriver info: driver.version: ChromeDriver\nselenide.url: https://www.stackoverflow.com\nselenide.baseUrl: http://localhost:8080\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)\n\tat org.openqa.selenium.remote.ErrorHandler.createThrowable(ErrorHandler.java:214)\n\tat org.openqa.selenium.remote.ErrorHandler.throwIfResponseFailed(ErrorHandler.java:166)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$new$0(JsonWireProtocolResponse.java:53)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$getResponseFunction$2(JsonWireProtocolResponse.java:91)\n\tat org.openqa.selenium.remote.ProtocolHandshake.lambda$createSession$0(ProtocolHandshake.java:123)\n\tat java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)\n\tat java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:958)\n\tat java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:126)\n\tat java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:498)\n\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)\n\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)\n\tat java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:152)\n\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n\tat java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:464)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:126)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:73)\n\tat org.openqa.selenium.remote.HttpCommandExecutor.execute(HttpCommandExecutor.java:136)\n\tat org.openqa.selenium.remote.service.DriverCommandExecutor.execute(DriverCommandExecutor.java:83)\n\tat org.openqa.selenium.remote.RemoteWebDriver.execute(RemoteWebDriver.java:545)\n\tat org.openqa.selenium.remote.RemoteWebDriver.startSession(RemoteWebDriver.java:209)\n\tat org.openqa.selenium.remote.RemoteWebDriver.\u003cinit\u003e(RemoteWebDriver.java:132)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:181)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:168)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:157)\n\tat com.codeborne.selenide.webdriver.ChromeDriverFactory.create(ChromeDriverFactory.java:27)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.lambda$createWebDriver$0(WebDriverFactory.java:61)\n\tat java.util.Optional.map(Optional.java:215)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.createWebDriver(WebDriverFactory.java:61)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.createDriver(WebDriverThreadLocalContainer.java:231)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.getAndCheckWebDriver(WebDriverThreadLocalContainer.java:118)\n\tat com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver(WebDriverRunner.java:136)\n\tat com.codeborne.selenide.impl.Navigator.navigateToAbsoluteUrl(Navigator.java:68)\n\tat com.codeborne.selenide.impl.Navigator.open(Navigator.java:32)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:95)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:69)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.pages.HomePage.load(HomePage.java:19)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.steps.HomeSteps.homepage(HomeSteps.java:15)\n\tat ✽.I am on the homepage(src/test/resources/features/stackoverflow/Search.feature:18)\n",
  "status": "failed"
});
formatter.step({
  "name": "I search for \"selenium\"",
  "keyword": "When "
});
formatter.match({
  "location": "HomeSteps.search(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I select the first question",
  "keyword": "And "
});
formatter.match({
  "location": "QuestionSteps.selectFirstQuestion()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "the question is tagged with \"selenium\"",
  "keyword": "Then "
});
formatter.match({
  "location": "QuestionSteps.isQuestionTaggedWith(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "Use the search function",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@web"
    },
    {
      "name": "@android"
    },
    {
      "name": "@ios"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I am on the homepage",
  "keyword": "Given "
});
formatter.match({
  "location": "HomeSteps.homepage()"
});
formatter.result({
  "error_message": "org.openqa.selenium.WebDriverException: unknown error: cannot find Chrome binary\n  (Driver info: chromedriver\u003d2.38.552522 (437e6fbedfa8762dec75e2c5b3ddb86763dc9dcb),platform\u003dLinux 3.10.0-693.21.1.el7.x86_64 x86_64) (WARNING: The server did not provide any stacktrace information)\nCommand duration or timeout: 7 milliseconds\nBuild info: version: \u00273.11.0\u0027, revision: \u0027e59cfb3\u0027, time: \u00272018-03-11T20:26:55.152Z\u0027\nSystem info: host: \u0027spirals-vortex.lille.inria.fr\u0027, ip: \u0027172.17.0.4\u0027, os.name: \u0027Linux\u0027, os.arch: \u0027amd64\u0027, os.version: \u00273.10.0-693.21.1.el7.x86_64\u0027, java.version: \u00271.8.0_121\u0027\nDriver info: driver.version: ChromeDriver\nselenide.url: https://www.stackoverflow.com\nselenide.baseUrl: http://localhost:8080\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)\n\tat org.openqa.selenium.remote.ErrorHandler.createThrowable(ErrorHandler.java:214)\n\tat org.openqa.selenium.remote.ErrorHandler.throwIfResponseFailed(ErrorHandler.java:166)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$new$0(JsonWireProtocolResponse.java:53)\n\tat org.openqa.selenium.remote.JsonWireProtocolResponse.lambda$getResponseFunction$2(JsonWireProtocolResponse.java:91)\n\tat org.openqa.selenium.remote.ProtocolHandshake.lambda$createSession$0(ProtocolHandshake.java:123)\n\tat java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)\n\tat java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:958)\n\tat java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:126)\n\tat java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:498)\n\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)\n\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)\n\tat java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:152)\n\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n\tat java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:464)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:126)\n\tat org.openqa.selenium.remote.ProtocolHandshake.createSession(ProtocolHandshake.java:73)\n\tat org.openqa.selenium.remote.HttpCommandExecutor.execute(HttpCommandExecutor.java:136)\n\tat org.openqa.selenium.remote.service.DriverCommandExecutor.execute(DriverCommandExecutor.java:83)\n\tat org.openqa.selenium.remote.RemoteWebDriver.execute(RemoteWebDriver.java:545)\n\tat org.openqa.selenium.remote.RemoteWebDriver.startSession(RemoteWebDriver.java:209)\n\tat org.openqa.selenium.remote.RemoteWebDriver.\u003cinit\u003e(RemoteWebDriver.java:132)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:181)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:168)\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:157)\n\tat com.codeborne.selenide.webdriver.ChromeDriverFactory.create(ChromeDriverFactory.java:27)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.lambda$createWebDriver$0(WebDriverFactory.java:61)\n\tat java.util.Optional.map(Optional.java:215)\n\tat com.codeborne.selenide.webdriver.WebDriverFactory.createWebDriver(WebDriverFactory.java:61)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.createDriver(WebDriverThreadLocalContainer.java:231)\n\tat com.codeborne.selenide.impl.WebDriverThreadLocalContainer.getAndCheckWebDriver(WebDriverThreadLocalContainer.java:118)\n\tat com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver(WebDriverRunner.java:136)\n\tat com.codeborne.selenide.impl.Navigator.navigateToAbsoluteUrl(Navigator.java:68)\n\tat com.codeborne.selenide.impl.Navigator.open(Navigator.java:32)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:95)\n\tat com.codeborne.selenide.Selenide.open(Selenide.java:69)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.pages.HomePage.load(HomePage.java:19)\n\tat io.github.martinschneider.yasew.examples.stackoverflow.steps.HomeSteps.homepage(HomeSteps.java:15)\n\tat ✽.I am on the homepage(src/test/resources/features/stackoverflow/Search.feature:18)\n",
  "status": "failed"
});
formatter.step({
  "name": "I search for \"appium\"",
  "keyword": "When "
});
formatter.match({
  "location": "HomeSteps.search(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I select the first question",
  "keyword": "And "
});
formatter.match({
  "location": "QuestionSteps.selectFirstQuestion()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "the question is tagged with \"appium\"",
  "keyword": "Then "
});
formatter.match({
  "location": "QuestionSteps.isQuestionTaggedWith(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "status": "passed"
});
});