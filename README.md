# TBME TV

A free to use streaming application.

[![Build Status](https://travis-ci.org/tbmelabs/tbme-tv.svg?branch=master)](https://travis-ci.org/tbmelabs/tbme-tv)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=https://sonarcloud.io/api/badges/gate?key=ch.tbmelabs:tv:master)](https://sonarcloud.io/dashboard/index/ch.tbmelabs:tv:master)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See [deployment](https://github.com/tbmelabs/tbme-tv/tree/master#deployment) for notes on how to deploy the project on a live system.

### Prerequisites

Make sure to install the following software before starting:

* [Java Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html): [This guide](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) leads you through the installation.
* [Maven](https://maven.apache.org/download.cgi): Have a look at [this page](https://maven.apache.org/install.html) for any help while installing.
* [Node.js and npm](https://nodejs.org/en/download): Please follow the instructions in the installer.
* [git](https://git-scm.com/downloads): Follow [this tutorial](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) to install git.
* [PostgreSQL](https://www.postgresql.org/download): There are [detailed installation guides](https://wiki.postgresql.org/wiki/Detailed_installation_guides) available if you need any help.

### Installing

#### PostgreSQL

This application requires an installed and prepared PostgreSQL database to run. Login to the postgresql database via root account and execute the following sql-scripts:

* core-server/authorization-server/src/main/resources/db/installation/auth_database.sql
* core-server/authorization-server/src/main/resources/db/installation/development/jdbc_token_store.sql

All servers and services have predefined development properties. Navigate into any Maven module containing a runnable application and execute `mvn clean spring-boot:run -Dspring.profiles.active=dev` to make use of those. Some modules might require Maven arguments as well.

If you differ any configuration: Change the value via Maven arguments, not by adapting the spring properties.

#### Backend development

For active backend development navigate to the according Maven module in your console. Run the following command to execute the runnable application in development mode:

```
mvn clean spring-boot:run -Dspring.profiles.active=dev
```

The server or service will now automatically reload upon saving changes.

#### Frontend development

For active frontend development navigate to the according Maven module and `src/main/webapp` in your console. Run the following command to execute the application in development mode:

```
npm run dev
```

We use [Webpack](https://webpack.js.org/) to package the frontend libraries. Started in development mode it will reload your bundled files automatically on saving changes.

## Running the tests

All tests will be executed automatically within `mvn package`. This includes Java (JUnit) and JavaScript/React (Karma) tests.

Use `mvn test` to execute the JUnit tests only. Nearly every Maven module includes some test cases.

Both the modules core-server/zuul-entry-server and core-server/authorization-server include frontend tests. Navigate to src/main/webapp and execute `npm run test` to start the Karma tests.

<!--
### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```
-->

## Deployment

You have to install [PostgreSQL](https://www.postgresql.org/) before deploying anything. Any attempt to execute `mvn test` without the proper database setup will fail. Login with your root account after the successful installation and execute the following sql-files to create the test users and databases:

* core-server/authorization-server/src/main/resources/db/installation/development/test_database.sql

### .war-files to Tomcat

The default build generates .war-files using Maven. Open a command line and execute `mvn clean install` to generate your copy of the project.

### Docker images

You may run the full application stack on Docker. It is packaged using the [com.spotify.docker-maven-plugin](https://mvnrepository.com/artifact/io.fabric8/docker-maven-plugin). To generate images execute `mvn clean package docker:build` in the specific folder. Currently dockerized Maven modules:

* core-server/eureka-service-discovery
* core-server/authorization-server
* core-server/zuul-entry-server

## Built With

* [npm](https://www.npmjs.com/) - The package manager for JavaScript and the world’s largest software registry.
* [React](https://reactjs.org/) - A JavaScript library for building user interfaces.
* [Maven](https://maven.apache.org/) - A software project management and comprehension tool.
* [Spring-Cloud](https://projects.spring.io/spring-cloud/) - Provides tools to quickly build some of the common patterns in distributed systems.

## Contributing

Please read [CONTRIBUTING.md](https://github.com/tbmelabs/tbme-tv/blob/master/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/tbmelabs/tbme-tv/tags). 

## Authors

* **Timon Borter** - *Initial work* - [bbortt](https://github.com/bbortt)

See also the list of [contributors](https://github.com/tbmelabs/tbme-tv/contributors) who participated in this project.

## License

TBME TV is a free to use streaming application.
Copyright (C) 2017 [TBME Labs](https://tbmelabs.ch)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

<!--
## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
-->
