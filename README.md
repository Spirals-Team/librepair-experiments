# repairnator-experiments

This is an open-science repository whith data related to ["How to Design a Program Repair Bot? Insights from the Repairnator Project"](https://hal.inria.fr/hal-01691496/document) (ICSE 2018) and [Repairnator patches programs automatically](https://ubiquity.acm.org/article.cfm?id=3349589), (Ubiquity, Association for Computing Machinery, 2019)

```Bibtex
@inproceedings{repairnator,
 title = {{How to Design a Program Repair Bot? Insights from the Repairnator Project}},
 author = {Urli, Simon and Yu, Zhongxing and Seinturier, Lionel and Monperrus, Martin},
 booktitle = {{Proceedings of the 40th International Conference on Software Engineering}},
 year = {2018}
}
```

This repository aims to contain the code of the failing Java projects and also information about the bugs reproduction, test failures and repair attempt logs using three different repair tools: [NPEFix](https://github.com/SpoonLabs/npefix), [Nopol](https://github.com/SpoonLabs/nopol), and [Astor](https://github.com/SpoonLabs/astor).

## Content of the repository

The structure of the repository is as follows:
* Branch `master` contains the `builds` folder and the documentation of this repository;
* The `builds` folder contains a specific JSON file obtained using the Travis CI API for every Travis CI build failure related to Java programs that use Maven as building tool;
* For each of these failing builds, there is a specific branch with all the information related to the building of a project, bug reproduction, failing tests and repair attempts.

More details about every entry listed above will be provided in the subsequent sections.

## Overview

The repository contains the builds collected in the period between February 2017 and August 2018.

There are currently 14.137 build JSON files in the `builds` folder, each of them associated to a Continuous Integration (CI) build failure of a Java program that uses Maven as building tool.

The name of these JSON files corresponds to the Travis CI build identifier and their content is the result of the Travis CI API V3 ```GET /build/{build.id}```. More details can be found on the [official documentation](https://docs.travis-ci.com/user/developer/#api-v3).

There are 14.137 different branches (excluding master branch), each of them associated to a build failure. In particular, every branch can have 2, 3 or 4 commits, as reported in the following table:

|                            | 2 commits   | 3 commits   | 4 commits   |
|----------------------------|:-----------:|:-----------:|:-----------:|
| **Number of the branches** | 15          | 13.875      | 247         |

Most of the branches have 3 commits, where:
* The first one is the failing one;
* The second one contains metadata and log infomation regarding the building of the project;
* The third one, optional, contains a patch developed by an automatic program repair techinque.

The branches that have 2 commits have the same information of the branches with 3 commits, and the only difference is that it has been created a single commit with all the data that usually are separated in the second and the third commit.

The branches that have 4 commits have one commit (the third one) that contains also the human patch.

## Builds Information extracted from the Travis CI API

A build can have different states, and as reported in the [official documentation of Travis CI](https://docs.travis-ci.com/user/for-beginners/#breaking-the-build), a build is considered broken when one or more of its jobs complete with a state that is not passed: `errored`, `failed`, or `canceled`.

In the following table, we reported the number of the collected builds based on their state:

|                            | failed      | passed      | errored     | canceled  |
|----------------------------|:-----------:|:-----------:|:-----------:|:---------:|
| **Number of the builds**   | 13.181      | 896         | 48          | 12        |

Every build is triggered by an event. In particular, in the following table it is possible to see the number of the collected builds that have been triggered by a specific event:

|                            | push        | pull request  | cron        | api      |
|----------------------------|:-----------:|:-------------:|:-----------:|:--------:|
| **Number of the builds**   | 7.172       | 6.266         | 539         | 160      |

In addition to the classic events (`push` and `pull request`), there can be other two types of triggering events: `cron` and `pull request`. 

The event type `cron` means that the build has been triggered via the [Travis CI cron jobs](https://docs.travis-ci.com/user/cron-jobs/), that can run builds at regular scheduled intervals independently of whether any commits were pushed to the repository.

The event type `api` means that the build has been triggered using the Travis CI API.

Using the information extracted from the Travis CI API, it is possible to know also the duration of a build, where the term `duration` is intended as the wall clock time in seconds to produce and execute a build.

The following table shows the number of the builds per different intervals of duration:

|                                            | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **0 &lt; duration &le; 100**               |                 1.527  |
| **100 &lt; duration &le; 500**             |                 3.950  |
| **500 &lt; duration &le; 1.000**           |                 1.443  |
| **1.000 &lt; duration &le; 5.000**         |                 3.400  |
| **5.000 &lt; duration &le; 10.000**        |                 1.220  |
| **10.000 &lt; duration &le; 25.000**       |                 2.172  |
| **25.000 &lt; duration &le; 50.000**       |                   408  |
| **duration &gt; 50.000**                   |                    17  |

The minimum duration recorded has been 3 seconds (`build` [357685462](https://github.com/repairnator/repairnator-experiments/blob/master/builds/357685462.json), `branch` [as0kir-topjava-357685462-20180324-053259](https://github.com/repairnator/repairnator-experiments/tree/as0kir-topjava-357685462-20180324-053259)), while the maximum duration recorded has been 60.035 seconds (`build` [247511890](https://github.com/repairnator/repairnator-experiments/blob/master/builds/247511890.json), `branch` [apache-flink-247511890-20170627-234202_bugonly](https://github.com/repairnator/repairnator-experiments/tree/apache-flink-247511890-20170627-234202_bugonly)).

## Builds Information extracted using Repairnator

The use of Repairnator allowed to collect a set of data about the bug reproduction, test failures and repair attempts. These data are stored in a specific branch for every build failure. In particular, every branch is linked to a build JSON file in `master` branch and, as well as containing the source code of the buggy program, it also has some files with the keyword `repairnator`in their name. These files contain the information collected by Repairnator.

### repairnator.json files

Every branch has a file called `repairnator.json` that contains the data reported in the following table:

| Parameter                | Description                                                                               |
|:-------------------------|:------------------------------------------------------------------------------------------|
| bugType                  | The type of the failing build.                                                             |
| bugCommit                | The URL of the bug commit in which it is possible to see the diff with the previous one.  |
| error-types              | The types of the errors occurred during the building of the project.                      |
| failingModule            | The failing module during the building of the project.                                    |
| failing-test-cases       | The list of the failing test cases.                                                       |
| hostname                 | The hostname used to run the Repairnator inspector.                                          |
| metrics                  | It contains information about the program associated with the failing build, such as the number of the source code files, the number of the test cases and the number of the used libraries. It also contains other information, such as the duration expressed in seconds to reproduce the bug, the date of the buggy build, and the date of the bug reproduction.                                                                                                      |
| repo                     | The name of the repository associated with the failing build.                             |
| totalNumberErroringTests | The total number of the erroring test cases.                                              |
| totalNumberFailingTests  | The total number of the failing test cases.                                               |
| totalNumberRunningTests  | The total number of the running test cases.                                               |
| totalNumberSkippingTests | The total number of the skipping test cases.                                              |

### Repairnator Maven Log Files

Repairnator also collects information about the different phases of the Maven build life cycle. In particual, there are four different files. It is possible to see the number of the branches that have these files in the following table:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.maven.buildproject.log        |                 12.860 |
| repairnator.maven.testproject.log         |                 12.860 |
| repairnator.maven.computeclasspath.log    |                 12.804 |
| repairnator.maven.resolvedependency.log   |                  7.845 |

### Repairnator Nopol Files

Nopol is one of the three program repair tools used in order to try to generate a patch for the failing builds. Nopol has been used since February 2017. The file `repairnator.nopol.results` contains information about its execution, such as its configuration and the allocated time. In the following table it is possible to see the number of the branches that contain this file:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.nopol.results                 |                  8.914 |

### Repairnator NPEFix Files

NPEFix is the second program repair tool used during the experiment and it has been used since August 2017. In particular, it has been exploited only when a `NullPointerException` has been encountered during the execution of the test suite. In the following table there are the different files associated with the execution of NPEFix and the number of branches that have these files:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.maven.nperepair.log           |                    922 |
| repairnator.maven.npefix.log              |                    715 |
| repairnator.npefix.results                |                    289 |

### Repairnator Astor Files

Astor is the third program repair tool used during the experiment and it has been used starting from September 2017. There are 4 log files related to the execution of Astor that can be found in the branches, as reported in the following table:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.astor.log                     |                  6.218 |
| repairnator.astor.mutation.log            |                  4.835 |
| repairnator.astor.results.json            |                  1.083 |
| repairnator.astor.mutation.results.json   |                    945 |




Analyzing the `repairnator.json` files, it is possible to know that are two main `bug type` associated to every build:
* only_fail;
* failing_passing.

In the following table, we reported the number of the builds for both of them:

|                            | only_fail   | failing_passing |     
|----------------------------|:-----------:|:---------------:|
| **Number of the builds**   |      13.390 |             747 |

In particular, removing the custom exceptions related to a specific program, we analyzed that the top 20 common failure types are the following ones:

| Failure type                                  | Number of the builds |
|:----------------------------------------------|---------------------:|
| java.lang.AssertionError                      |                7.280 |
| java.lang.NullPointerException                |                1.836 |
| org.junit.ComparisonFailure                   |                1.293 |
| java.lang.IllegalStateException               |                1.280 |
| java.lang.AssertionError                      |                1.198 |
| java.lang.RuntimeException                    |                  736 |
| java.lang.Exception                           |                  722 |
| java.lang.NoClassDefFoundError                |                  442 |
| junit.framework.AssertionFailedError          |                  433 |
| java.lang.IllegalArgumentException            |                  420 |
| java.io.IOException                           |                  404 |
| org.junit.runners.model.TestTimedOutException |                  276 |
| java.lang.NullPointerException:               |                  261 |
| java.lang.ExceptionInInitializerError         |                  261 |
| java.io.FileNotFoundException                 |                  154 |
| junit.framework.ComparisonFailure             |                  136 |
| java.lang.IndexOutOfBoundsException           |                  117 |
| java.util.concurrent.TimeoutException         |                   99 |
| java.net.MalformedURLException                |                   92 |
| org.junit.AssumptionViolatedException         |                   90 |

On the contrary, considering only the custom exceptions, we analyzed that the top 10 common failure types are the following ones:

| Failure type                                            | Number of the builds |
|:--------------------------------------------------------|---------------------:|
| skipped                                                 |                  346 |
| org.postgresql.util.PSQLException                       |                  228 |
| redis.clients.jedis.exceptions.JedisConnectionException |                  173 |
| redis.clients.jedis.exceptions.JedisException           |                  163 |
| com.facebook.presto.spi.PrestoException                 |                  132 |
| org.springframework.beans.factory.BeanCreationException |                  112 |
| org.openqa.selenium.WebDriverException                  |                  105 |
| com.spotify.docker.client.exceptions.DockerException    |                   96 |
| spoon.SpoonException                                    |                   96 |
| com.mongodb.MongoTimeoutException                       |                   95 |

During the build process there can be many failing test cases. In the following table it is possible to see the number of the builds divided by the number of the failing tests:

| Number of the failing test cases           | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **1**                                      |                 3.141  |
| **2**                                      |                 2.958  |
| **3**                                      |                   545  |
| **4**                                      |                   601  |
| **5**                                      |                   177  |
| **6**                                      |                   735  |
| **&gt; 10**                                |                   556  |

The number of the builds divided by the number of the erroring test cases is reported in the following table:

| Number of the erroring test cases          | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **1**                                      |                 2.210  |
| **2**                                      |                   733  |
| **3**                                      |                   531  |
| **4**                                      |                   297  |
| **5**                                      |                   233  |
| **6**                                      |                   273  |
| **&gt; 50**                                |                 1.162  |
