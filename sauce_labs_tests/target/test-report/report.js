$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("mooltipass/automatedTests/features/Tests.feature");
formatter.feature({
  "line": 1,
  "name": "First feature",
  "description": "",
  "id": "first-feature",
  "keyword": "Feature"
});
formatter.before({
  "duration": 127282810,
  "error_message": "java.lang.IllegalArgumentException: /root/workspace/mooltipass/extension/402957381/sauce_labs_tests/chrome_extension.crx does not exist\n\tat com.google.common.base.Preconditions.checkArgument(Preconditions.java:210)\n\tat org.openqa.selenium.chrome.ChromeOptions.addExtensions(ChromeOptions.java:166)\n\tat org.openqa.selenium.chrome.ChromeOptions.addExtensions(ChromeOptions.java:153)\n\tat mooltipass.automatedTests.config.WebDriverFactory.remoteChrome(WebDriverFactory.java:85)\n\tat mooltipass.automatedTests.config.WebDriverFactory.createDriver(WebDriverFactory.java:178)\n\tat mooltipass.automatedTests.config.WebDriverFactory.get(WebDriverFactory.java:35)\n\tat mooltipass.automatedTests.config.WebDriverFactory.get(WebDriverFactory.java:41)\n\tat mooltipass.automatedTests.config.BeforeAfter.getNewBrowser(BeforeAfter.java:45)\n\tat mooltipass.automatedTests.config.BeforeAfter.beforeScenario(BeforeAfter.java:22)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat cucumber.runtime.Utils$1.call(Utils.java:37)\n\tat cucumber.runtime.Timeout.timeout(Timeout.java:13)\n\tat cucumber.runtime.Utils.invoke(Utils.java:31)\n\tat cucumber.runtime.java.JavaHookDefinition.execute(JavaHookDefinition.java:60)\n\tat cucumber.runtime.Runtime.runHookIfTagsMatch(Runtime.java:223)\n\tat cucumber.runtime.Runtime.runHooks(Runtime.java:211)\n\tat cucumber.runtime.Runtime.runBeforeHooks(Runtime.java:201)\n\tat cucumber.runtime.model.CucumberScenario.run(CucumberScenario.java:40)\n\tat cucumber.runtime.junit.ExecutionUnitRunner.run(ExecutionUnitRunner.java:91)\n\tat cucumber.runtime.junit.FeatureRunner.runChild(FeatureRunner.java:63)\n\tat cucumber.runtime.junit.FeatureRunner.runChild(FeatureRunner.java:18)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat cucumber.runtime.junit.FeatureRunner.run(FeatureRunner.java:70)\n\tat cucumber.api.junit.Cucumber.runChild(Cucumber.java:93)\n\tat cucumber.api.junit.Cucumber.runChild(Cucumber.java:37)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat cucumber.api.junit.Cucumber.run(Cucumber.java:98)\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:252)\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:141)\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:112)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat org.apache.maven.surefire.util.ReflectionUtils.invokeMethodWithArray(ReflectionUtils.java:189)\n\tat org.apache.maven.surefire.booter.ProviderFactory$ProviderProxy.invoke(ProviderFactory.java:165)\n\tat org.apache.maven.surefire.booter.ProviderFactory.invokeProvider(ProviderFactory.java:85)\n\tat org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:115)\n\tat org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:75)\n",
  "status": "failed"
});
formatter.scenario({
  "line": 38,
  "name": "Etsy Login",
  "description": "",
  "id": "first-feature;etsy-login",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 39,
  "name": "I navigate to \u0027http://etsy_login.mooltipass-tests.com/ci/etsy_login/\u0027",
  "keyword": "Given "
});
formatter.step({
  "line": 40,
  "name": "I go to Etsy login page",
  "keyword": "When "
});
formatter.step({
  "line": 41,
  "name": "I login to Etsy Login with \u0027mooltipass\u0027",
  "keyword": "When "
});
formatter.step({
  "line": 42,
  "name": "I should be logged in Etsy Login",
  "keyword": "Then "
});
formatter.step({
  "line": 43,
  "name": "I navigate to \u0027http://etsy_login.mooltipass-tests.com/ci/etsy_login/\u0027",
  "keyword": "Given "
});
formatter.step({
  "line": 44,
  "name": "I go to Etsy login page",
  "keyword": "When "
});
formatter.step({
  "line": 45,
  "name": "I should be logged in Etsy Login",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "http://etsy_login.mooltipass-tests.com/ci/etsy_login/",
      "offset": 15
    }
  ],
  "location": "GitHubSteps.navigateToURL(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "EtsySteps.goTologinPage()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "mooltipass",
      "offset": 28
    }
  ],
  "location": "EtsySteps.login(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "EtsySteps.checkLogin()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "http://etsy_login.mooltipass-tests.com/ci/etsy_login/",
      "offset": 15
    }
  ],
  "location": "GitHubSteps.navigateToURL(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "EtsySteps.goTologinPage()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "EtsySteps.checkLogin()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 12600505,
  "error_message": "java.lang.NullPointerException\n\tat mooltipass.automatedTests.config.BeforeAfter.afterScenario(BeforeAfter.java:35)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat cucumber.runtime.Utils$1.call(Utils.java:37)\n\tat cucumber.runtime.Timeout.timeout(Timeout.java:13)\n\tat cucumber.runtime.Utils.invoke(Utils.java:31)\n\tat cucumber.runtime.java.JavaHookDefinition.execute(JavaHookDefinition.java:60)\n\tat cucumber.runtime.Runtime.runHookIfTagsMatch(Runtime.java:223)\n\tat cucumber.runtime.Runtime.runHooks(Runtime.java:211)\n\tat cucumber.runtime.Runtime.runAfterHooks(Runtime.java:205)\n\tat cucumber.runtime.model.CucumberScenario.run(CucumberScenario.java:46)\n\tat cucumber.runtime.junit.ExecutionUnitRunner.run(ExecutionUnitRunner.java:91)\n\tat cucumber.runtime.junit.FeatureRunner.runChild(FeatureRunner.java:63)\n\tat cucumber.runtime.junit.FeatureRunner.runChild(FeatureRunner.java:18)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat cucumber.runtime.junit.FeatureRunner.run(FeatureRunner.java:70)\n\tat cucumber.api.junit.Cucumber.runChild(Cucumber.java:93)\n\tat cucumber.api.junit.Cucumber.runChild(Cucumber.java:37)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat cucumber.api.junit.Cucumber.run(Cucumber.java:98)\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:252)\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:141)\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:112)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat org.apache.maven.surefire.util.ReflectionUtils.invokeMethodWithArray(ReflectionUtils.java:189)\n\tat org.apache.maven.surefire.booter.ProviderFactory$ProviderProxy.invoke(ProviderFactory.java:165)\n\tat org.apache.maven.surefire.booter.ProviderFactory.invokeProvider(ProviderFactory.java:85)\n\tat org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:115)\n\tat org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:75)\n",
  "status": "failed"
});
});