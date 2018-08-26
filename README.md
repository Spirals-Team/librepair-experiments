# hps-java-junit

 [![Build Status](https://travis-ci.org/Lagarith/smart-logistic-tests.svg?branch=master)](https://travis-ci.org/Lagarith/smart-logistic-tests)

Hiptest publisher samples with Java/JUnit

In this repository you'll find tests generated in junit format from [Hiptest](https://hiptest.com), using [Hiptest publisher](https://github.com/hiptest/hiptest-publisher).

The goals are:

 * to show how tests are exported in JUnit.
 * to check exports work out of the box (well, with implemented actionwords in java)

System under test
------------------

The SUT is a (not that much) simple coffee machine. You start it, you ask for a coffee and you get it, sometimes. But most of times you have to add water or beans, empty the grounds. You have an automatic expresso machine at work or at home? So you know how it goes :-)

Update tests
-------------


To update the tests:

    hiptest-publisher -c junit.conf --only=tests

The tests are generated in [``src/test/java/com/basic/ProjectTest.java``](https://github.com/hiptest/hps-java-junit/blob/master/java/src/test/java/com/coffeemachine/ProjectTest.java)

Run tests
---------


First, you must use [``maven``](http://maven.apache.org/) to build the project and run the tests:

    mvn package

The SUT implementation can be seen in [``src/main/java/com/coffeemachine/CoffeeMachine.java``](https://github.com/hiptest/hps-java-junit/blob/master/java/src/main/java/com/coffeemachine/CoffeeMachine.java)

By default test report is generated in ```target/surefire-reports/TEST-com.coffeemachine.ProjectTest.xml```

