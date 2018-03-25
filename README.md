[![Build Status](https://travis-ci.org/OhmGeek/JDCraw.svg?branch=master)](https://travis-ci.org/OhmGeek/JDCraw)
[![codebeat badge](https://codebeat.co/badges/5b0bcc7b-10af-406f-84f8-5cb78b990a11)](https://codebeat.co/projects/github-com-ohmgeek-jdcraw-master)
[![codecov](https://codecov.io/gh/OhmGeek/JDCraw/branch/master/graph/badge.svg)](https://codecov.io/gh/OhmGeek/JDCraw)
# JDCraw
## Introduction
JDCraw is a wrapper around the popular dcraw executable.

The current system is very much **in development**, and as such, I wouldn't yet use it in a production system. 

## Dependencies
The project relies on Maven.

This project relies on the [DCRaw executable](https://www.cybercom.net/~dcoffin/dcraw/dcraw.1.html), built by Dave Coffin. Depending on your operating system, you can install it as follows:
### DCRaw Executable
#### Linux:
Install using the built in package manager.

For Debian/Ubuntu:
```bash
sudo apt-get install dcraw
```

For Fedora:
```bash
sudo yum install dcraw
```

#### macOS
First, install the [brew](https://github.com/Homebrew/brew) package manager, if you haven't already.

Then, open Terminal and run the following:

```bash
sudo brew install dcraw
```
## Usage
Using JDCraw is very simple. Using a Java File object, simply create an instance  of DCRawManager passing the file in: 

```java
File fileToEdit = new File('/path/to/file.dng');

DCRawManager manager = new DCRawManager(fileToEdit);
```

Then, one can use this manager object in several ways.

### Operations
DCRaw arguments are expressed in pure English. Let's say I want to flip an image by 90 degrees. Simply
create an operation that does that:
```java
RawOperation flipOperation = 
            new FlipImageOperation(FlipAngleEnum.DEGREES90);
```
Then, add it to the list of operations for DCraw to carry out:

```java
...

manager.addOperation(flipOperation);
```

Simple!

## Tests
There are some tests, but these are not entirely comprehensive. To run the ones present, use Maven:

```bash
mvn test
```

## License
Licensed under the MIT License.

DCRaw is licensed under the LGPLv2 license. 
