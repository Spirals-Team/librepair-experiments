# Status of master branch

[![Build Status](https://travis-ci.org/joshchamberlain/CS471-Assignments-UMLIntro-GitHubTest2.svg?branch=master)](https://travis-ci.org/joshchamberlain/CS471-Assignments-UMLIntro-GitHubTest2)

![Sonar Status](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=alert_status)

![Bugs](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=bugs)

![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=code_smells)

![Test Coverage](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=coverage)

![Duplicate Lines](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=duplicated_lines_density)

![LOC](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=ncloc)

![Security](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=sqale_rating)

![Reliability](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=reliability_rating)

![Security](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=security_rating)

![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=sqale_index)

![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=edu.boisestate.cs471%3Asorting&metric=vulnerabilities)

[![Maintainability](https://api.codeclimate.com/v1/badges/83d3e1b3bb55ec1867db/maintainability)](https://codeclimate.com/github/joshchamberlain/CS471-Assignments-UMLIntro-GitHubTest2/maintainability)

[![Test Coverage](https://api.codeclimate.com/v1/badges/83d3e1b3bb55ec1867db/test_coverage)](https://codeclimate.com/github/joshchamberlain/CS471-Assignments-UMLIntro-GitHubTest2/test_coverage)

# Overview
The source code is hosted on GitHub, built by Travis CI, and analyzed by Code Climate and Sonar Cloud

## Travis
The travis configuration is contained almost entirely in the `<project>/.travis.yml` file. There are two secret
values. One is an API token to GitHub, and the other is a token for Sonar Cloud. 
 * Requires GUI: No
 * Requires CLI Tool: Yes, for initial setup
 * Requires Config File: Yes, `.travis.yml`

Gotchas:
 * travis-ci.org versus travis-ci.com. Make sure you are only using .org. Otherwise, any encrypted environmental
 variables will fail.
 * Safest approach is to configure travis from the command line instead of using the GUI/browser
 
## Code Climate
This service analyzes the build.
 * Requires GUI: Yes
 * Requires CLI Tool: No
 * Requires Config File: Optional, we have one to enable extra plugins, `.codeclimate.yml`
 
Gotchas:
 * It does not automatically find unit test coverage reports. The `.travis.yml` configuration must be updated to upload
 the report to Code Climate.

## Sonar Cloud
Like Code Climate, this also analyzes the build. Sonar provides more useful trend graphs than Code Climate.
 * Requires GUI: Yes
 * Requires CLI Tool: No
 * Requires Config File: No
 
Gotcahs:
 * Authentication can be tricky. 
   1. The token should be encrypted using the Travis command line tool as follows:
    ```
    travis encrypt SONAR_TOKEN=<token>
    ```
   2. Add the token to the global environmental variables in the `.travis.yml` file
   3. Use the token by adding `-Dsonar.login=${SONAR_TOKEN}` when having Travis run the sonar:sonar maven goal. 

## Commit Hooks
There is a commit hook that prevents adding tabs to Java files.

# Creating Releases
The travis configuration will create a release for any tagged build. The suggested release process is to use the
script `<project>/releng/release`. This does the following:
 * Updates the version number in `pom.xml`
 * Creates a git commit with those changes, prompting for a commit message
 * Promt to confirm, then create a tag and push the tag
 * Once the tag is pushed, travis will run a special deploy section that will send the packaged jar file to GitHub

Example Usage:
```bash
  $ ./releng/release minor
```

# Abandoned Ideas
Some technologies were investigated but decided against. Here are some notes for future reference.

## GitLab
GitLab has some nice features, but you do not have complete control. This means you rely heavily on GitLab for 
integrations to work. GitHub already has tons of integrations that 'just work', and their configuration is fairly
transparent.

## Coverity
It may provide some nice features, but it requires uploading archives to it. Given the high quality and relative
simplicity of Sonar and Code Climate, it was not worth pursuing Coverity.

## AppVeyor
AppVeyor can display a JUnit test report. However, it can be complicated to set up.

I suggest using AppVeyor if:
 * You prefer Windows over Linux
 * You are using .NET

## Storing Build Artifacts
I was trying to store build artifacts (test results, test coverage, checkstyle, etc.) so that I could create some trend
graphs. However, it turns out that Sonar Cloud provides sufficient trend graphs, so there is no need to store build
artifacts. I had checked into the following approaches and was having some difficulty:
 * Heroku
 * GitLab
 * Coverity
 * Appveyor

## Jenkins
Jenkins is a great tool, but it takes a considerable amount of effort to configure. There are free hosted options, but
the only one I could get to work was really slow. I finally got it to start building only for the build to fail because
it didn't have maven installed. I did not have permissions to install maven onto it.

I suggest using Jenkins if:
 * You have root access to a server
 * You are using a Java project with Maven
 * You have a team of <20 people
 * You trust your team not to break Jenkins
 * You have experience with Jenkins

