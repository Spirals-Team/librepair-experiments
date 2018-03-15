[![Build Status](https://travis-ci.org/CS-Worcester-CS-348-SP-2018/ci-exercise.svg?branch=master)](https://travis-ci.org/CS-Worcester-CS-348-SP-2018/ci-exercise)

*Version 2018-Spring-1.1-Final, Revised 14 March 2018*
### *CS-348 01, 02 &mdash; Spring 2018*

# Continuous Integration Exercise

## Set up your Repository

### Fork the Repository
[https://github.com/CS-Worcester-CS-348-SP-2018/ci-exercise](https://github.com/CS-Worcester-CS-348-SP-2018/ci-exercise)

### Clone the Repository

### Add the original repository as an upstream remote

## Trying out Maven

### Build with Maven

```
mvn clean compile
```

### Run JUnit Tests with Maven
```
mvn test
```

### Build JAR File with Maven and Run
```
mvn clean package
java -jar target/CIExercise-0.0.1-SNAPSHOT.jar
```

### View Travis CI Builds
[https://travis-ci.org/CS-Worcester-CS-348-SP-2018/ci-exercise](https://travis-ci.org/CS-Worcester-CS-348-SP-2018/ci-exercise)

### Look at Maven Configuration
Look at the `pom.xml` file in the CIExercise

### Look at the Travis Configuration
Look at the `.travis.yml` file in the CIExercise

### Look at the Git Configuration
Look at the `.gitignore` file in the CIExercise

## Modify the CIExercise to Include your Name

I am *intentionally* not repeating all of the Git and GitHub commands for you here. You should be getting used to what you need to do. If you cannot remember how do the following steps ***in order***:

1. Look at past in-class exercises and homework assignments.
2. Ask your classmates for help.
3. Ask me for help.
 
###  Create an `add-name` branch, and switch to that branch

### Edit the code
Based on the last digit of your WSU Student ID, edit the appropriate `EndsWith` class. For example, my ID ends with `2`, so I edited the `EndsWith2.java` file. Look at `EndsWith2.java` as an example for what to do.

### Build the code with Maven

### Test the code with Maven
The test will fail because you have not updated the test file for the class you edited.

Go update the test for the code you just wrote and test again.

### Build JAR File with Maven and Run

### Before committing your changes, make sure your repository is up-to-date with upstream
Pull recent changes from your classmates

Resolve any conflicts

### Add your code and commit your changes

### Push changes

### Make a pull request to have your change merged into the original repository

### Go to the original repository and approve the pull request

### Check Travis

# Reference Material

## Git Workflow Reminder
> 
> 1. Add the change
> 2. Pull any changes that have ocurred since the last pull, and correct merge conflicts
> 3. Commit the change, with a message describing the change.
> 4. Push the change
> 5. Make a pull request to have the change merged into the original repository


## Working With Linux Bash on Windows 10

* Determine where you want to clone your repository to on your Windows file system
* `cd /mnt/c/Users/your-login-name` will get you to the top of your home directory tree on Windows (assuming that it is on your C: drive.)
* Continue using `cd` and `ls` to get yourself into whichever directory you determined you want to use.
* Once there, clone the repository.
* Issue Maven and Git commands there.
* You can use your preferred editor in Windows to edit files.

## General Bash  Hints

* To view hidden files (starting with .) in Bash, type `ls -al`


## Copyright and License
#### &copy; 2018 Karl R. Wurst, Worcester State University

<img src="http://mirrors.creativecommons.org/presskit/buttons/88x31/png/by-sa.png" width=100px/>This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit <a href="http://creativecommons.org/licenses/by-sa/4.0/" target="_blank">http://creativecommons.org/licenses/by-sa/4.0/</a> or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

