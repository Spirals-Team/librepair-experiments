@Ignore
@atf4j
Feature: Smoke test for Selenium WebDriver configuration
	As an Automated Tester
	I want a quick and easy way to use Selenium WebDriver
	In order to smoke test Selenium WebDriver configuration

@Ignore
Scenario: Open URL with headless browser
	Given a web browser is available
	When the target url is opened
	Then the pageTitle is "Apache Tomcat"

@Ignore
Scenario: Open URL with Firefox Browser
	Given the Firefox Browser
	Given the web browser is Firefox
	When the target url "http://127.0.0.1:8080/" is opened
	Then the pageTitle is (Apache Tomcat)
	And the cookie (name) exists

@Ignore
Scenario: Open URL with Chrome Browser
	Given the Chrome Browser
	Given the web browser is Chrome
	When the target url is opened
	Then the pageTitle is 'Apache Tomcat'
	And the cookie 'name' exists

@Ignore
Scenario: Open URL with Chromium Browser
	Given the Chromium Browser
	Given the web browser is Chromium
	When the target url 'http://127.0.0.1:8080' is opened
	Then the pageTitle is 'Apache Tomcat'
	And the cookie 'name' exists

@Ignore
Scenario: Additional Steps
	When the link (Text) is clicked
	When the button (Name) is clicked
	When the text (text) is entered into the control (Name)
	When the target Url http://127.0.0.1:8080 is opened
	Then the pageTitle is "Apache Tomcat"
	And the cookie "name" exists
