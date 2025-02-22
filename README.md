# repairnator-experiments

This is an open-science repository with data related to ["How to Design a Program Repair Bot? Insights from the Repairnator Project"](https://hal.inria.fr/hal-01691496/document) (ICSE 2018) and [Repairnator patches programs automatically](https://ubiquity.acm.org/article.cfm?id=3349589), (Ubiquity, Association for Computing Machinery, 2019).

```Bibtex
@inproceedings{repairnator,
 title = {How to Design a Program Repair Bot? Insights from the Repairnator Project},
 author = {Urli, Simon and Yu, Zhongxing and Seinturier, Lionel and Monperrus, Martin},
 booktitle = {Proceedings of the 40th International Conference on Software Engineering},
 year = {2018}
}
```

This repository aims to contain the code of the failing Java projects and also information about the bugs reproduction, test failures and repair attempts using three different repair tools: [NPEFix](https://github.com/SpoonLabs/npefix), [Nopol](https://github.com/SpoonLabs/nopol), and [Astor](https://github.com/SpoonLabs/astor).

## Content of the repository

The structure of the repository is as follows:
* Branch `master` contains the `builds` folder and the documentation of this repository;
* The `builds` folder contains a specific JSON file obtained using the Travis CI API for every Travis CI build failure related to Java programs that use Maven as building tool;
* The `jobs` folder contains a subfolder for every build and each of them contains as many JSON files (obtained using the Travis CI API) as the number of jobs associated with that build;
* For each of these failing builds, there is a specific branch with all the information related to the building of the project, bug reproduction, failing tests and repair attempts.

More details about every entry listed above will be provided in the subsequent sections.

## Statistics

The repository contains the builds collected in the period February 2017 - September 2018.

There are currently 14.132 build JSON files in the `builds` folder, each of them associated with a Continuous Integration (CI) build failure of a Java program that uses Maven as building tool.

The name of these JSON files corresponds to the Travis CI build identifier and their content is the result of the Travis CI API V3 ```GET /build/{build.id}```. More details can be found on the [official documentation](https://docs.travis-ci.com/user/developer/#api-v3).

The `jobs` folder has a specific subfolder for every build (except for 25 builds in which the data are no more available through the Travis CI API) and the name of these subfolders corresponds with the build id to which they are associated. Every subfolder related to a build contains a distinct JSON file for every job associated with that build. The name of these JSON files corresponds to the Travis CI job identifier and their content is the result of the Travis CI API V3 ```GET /job/{job.id}```.

There are 14.132 different branches (excluding master branch), each of them associated with a build failure. In particular, every branch can have 2, 3 or 4 commits, as reported in the following table:

|                            | 2 commits   | 3 commits   | 4 commits   |
|----------------------------|:-----------:|:-----------:|:-----------:|
| **Number of the branches** | 15          | 13.870      | 247         |

Most of the branches have 3 commits, where:
* The first one is the failing one;
* The second one contains metadata and log infomation regarding the building of the project;
* The third one adds other information about the failure and the reproduction of the bug. In addition, it can contain a patch developed by an automatic program repair technique, if any.

The branches that have 2 commits have the same information of the branches with 3 commits, and the only difference is that it has been created a single commit with all the data that usually are separated in the second and the third commit.

The branches that have 4 commits have one commit (the third one) that contains also the human patch.

## Builds information extracted using Travis CI API

### States of the collected failing builds

A build can have different states, and as reported in the [official documentation of Travis CI](https://docs.travis-ci.com/user/for-beginners/#breaking-the-build), a build is considered broken when one or more of its jobs complete with a state that is not passed: `errored`, `failed`, or `canceled`.

In the following table, it is reported the number of collected builds based on their state:

|                            | failed      | passed      | errored     | canceled  |
|----------------------------|:-----------:|:-----------:|:-----------:|:---------:|
| **Number of builds**       | 13.178      | 895         | 47          | 12        |

### Trigger events of the collected failing builds

Every build is triggered by an event. In particular, in the following table we see the number of the collected builds that have been triggered by a specific event:

|                            | push        | pull request  | cron        | api      |
|----------------------------|:-----------:|:-------------:|:-----------:|:--------:|
| **Number of builds**       | 7.169       | 6.264         | 539         | 160      |


The event type `cron` means that the build has been triggered via the [Travis CI cron jobs](https://docs.travis-ci.com/user/cron-jobs/), that can run builds at regular scheduled intervals independently of whether any commits were pushed to the repository.

The event type `api` means that the build has been triggered using the Travis CI API.

### Duration of the collected failing builds

Using the information extracted from the Travis CI API, it is possible to know the duration of a build, where the term `duration` is intended as the wall clock time in seconds to produce and execute a build.

The following table shows the number of the builds per different intervals of duration:

|                                            | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **0 &lt; duration &le; 100**               |                 1.527  |
| **100 &lt; duration &le; 500**             |                 3.948  |
| **500 &lt; duration &le; 1.000**           |                 1.443  |
| **1.000 &lt; duration &le; 5.000**         |                 3.397  |
| **5.000 &lt; duration &le; 10.000**        |                 1.220  |
| **10.000 &lt; duration &le; 25.000**       |                 2.172  |
| **25.000 &lt; duration &le; 50.000**       |                   408  |
| **duration &gt; 50.000**                   |                    17  |

The minimum duration recorded has been 3 seconds (`build` [357685462](https://github.com/repairnator/repairnator-experiments/blob/master/builds/357685462.json), `branch` [as0kir-topjava-357685462-20180324-053259](https://github.com/repairnator/repairnator-experiments/tree/as0kir-topjava-357685462-20180324-053259)), while the maximum duration recorded has been 60.035 seconds (`build` [247511890](https://github.com/repairnator/repairnator-experiments/blob/master/builds/247511890.json), `branch` [apache-flink-247511890-20170627-234202_bugonly](https://github.com/repairnator/repairnator-experiments/tree/apache-flink-247511890-20170627-234202_bugonly)).

## Jobs information extracted using Travis CI API

### States of the jobs associated with a build

Using the Travis CI API, it is possibile to know also the state of the jobs associated with a specific build. In particular, a job can have four different states: `passed`, `errored`, `failed`, and `canceled`. As reported in the [official documentation of Travic CI](https://docs.travis-ci.com/user/for-beginners/#breaking-the-build), when one or more jobs have a state different from `passed`, the associated build is considered broken.

In the following table, it is reported the number of jobs associated with the collected builds based on their state:

|                            | passed      | errored     | failed      | canceled  |
|----------------------------|:-----------:|:-----------:|:-----------:|:---------:|
| **Number of jobs**         | 37.738      | 144         | 23.962      | 2.945     |

### Number of errored jobs per build

When a job is classified as `errored`, it means that a command in the `before_install`, `install`, or `before_script` phase returned a non-zero exit code. The job stops immediately.

In the following table it is possibile to see the number of errored jobs per build:

| Number of the errored jobs | Number of the builds |
|----------------------------|---------------------:|
| 0                          |               14.003 |
| 1                          |                   87 |
| 2                          |                    8 |
| 3                          |                    3 |
| 4                          |                    5 |
| 5                          |                    0 |
| 6                          |                    0 |
| > 6                        |                    1 |

The highest number of the errored jobs per build is 12 (`build` [332193968](https://github.com/repairnator/repairnator-experiments/blob/master/builds/332193968.json), `branch` [GJL-flink-332193968-20180123-111656_bugonly](https://github.com/repairnator/repairnator-experiments/tree/GJL-flink-332193968-20180123-111656_bugonly)). 

### Number of failed jobs per build

When a job is classified as `failed`, it means that a command in the `script` phase returned a non-zero exit code. The job continues to run until it completes.

In the following table it is possibile to see the number of failed jobs per build:

| Number of the failed jobs | Number of the builds |
|---------------------------|---------------------:|
| 0                         |                  926 |
| 1                         |                8.554 |
| 2                         |                2.514 |
| 3                         |                  901 |
| 4                         |                  512 |
| 5                         |                  187 |
| 6                         |                  193 |
| > 6                       |                  320 |

The highest number of the failed jobs per build is 58 (`build` [421480538](https://github.com/repairnator/repairnator-experiments/blob/master/builds/421480538.json) - `branch` [JWGmeligMeyling-blaze-persistence-421480538-20180828-125625](https://github.com/repairnator/repairnator-experiments/tree/JWGmeligMeyling-blaze-persistence-421480538-20180828-125625), and `build` [421505900](https://github.com/repairnator/repairnator-experiments/blob/master/builds/421505900.json) - `branch` [JWGmeligMeyling-blaze-persistence-421505900-20180828-141855](https://github.com/repairnator/repairnator-experiments/tree/JWGmeligMeyling-blaze-persistence-421505900-20180828-141855)).

### Number of canceled jobs per build

When a job is classified as `canceled`, it means that a user cancels the job before it completes.

In the following table it is possibile to see the number of canceled jobs per build:

| Number of the canceled jobs | Number of the builds |
|-----------------------------|---------------------:|
| 0                           |               13.834 |
| 1                           |                  159 |
| 2                           |                   15 |
| 3                           |                    4 |
| 4                           |                    4 |
| 5                           |                    0 |
| 6                           |                    2 |
| > 6                         |                   89 |

The highest number of the canceled jobs per build is 36. The are 41 builds with 36 canceled jobs (e.g., the `build` [335090924](https://github.com/repairnator/repairnator-experiments/blob/master/builds/335090924.json), `branch` [matsim-org-matsim-335090924-20180130-113656_bugonly](https://github.com/repairnator/repairnator-experiments/tree/matsim-org-matsim-335090924-20180130-113656_bugonly)).

## Builds information extracted using Repairnator

The data about bugs reproduction, test failures and repair attempts is stored in a specific branch for every build failure. In particular, every branch is linked to a build JSON file in `master` branch and, as well as containing the source code of the buggy program, it also has some files with the keyword `repairnator`in their name. 

### repairnator.json files

Every branch has a file called `repairnator.json` that contains the data reported in the following table:

| Parameter                | Description                                                                               |
|:-------------------------|:------------------------------------------------------------------------------------------|
| bugType                  | The type of the failing build.                                                             |
| bugCommit                | The URL of the bug commit in which it is possible to see the diff with the previous one.  |
| error-types              | The types of errors that occurred during the building of the project.                      |
| failingModule            | The failing Maven module.                                    |
| failing-test-cases       | The list of the failing test cases.                                                       |
| hostname                 | The hostname used to run the Repairnator inspector.                                          |
| metrics                  | It contains information about the program associated with the failing build, such as the number of program files, the number of test cases and the number of used libraries. It also contains other information, such as the duration expressed in seconds to reproduce the bug, the date of the buggy build, and the date of the bug reproduction.                                                                                                      |
| repo                     | The name of the repository associated with the failing build.                             |
| totalNumberErroringTests | The total number of the erroring test cases.                                              |
| totalNumberFailingTests  | The total number of the failing test cases.                                               |
| totalNumberRunningTests  | The total number of the running test cases.                                               |
| totalNumberSkippingTests | The total number of the skipping test cases.                                              |

### Repairnator Maven files

Repairnator also collects information about the different phases of the Maven build life cycle. In particual, there are four different files. It is possible to see the number of the branches that have these files in the following table:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.maven.buildproject.log        |                 12.855 |
| repairnator.maven.testproject.log         |                 12.855 |
| repairnator.maven.computeclasspath.log    |                 12.799 |
| repairnator.maven.resolvedependency.log   |                  7.841 |

* **repairnator.maven.buildproject.log**: it contains log information about the building of a project, such as the operations related to the goals of Apache Maven Resources plugin;
* **repairnator.maven.testproject.log**: it contains information about the execution of the test suite, such as the name of the executed test cases, and the number of the failing, running or skipped tests;
* **repairnator.maven.computeclasspath.log**: it contains log information about the creation of the classpath file;
* **repairnator.maven.resolvedependency.log**: it contains log information about the download of the necessary dependecies in order to build the project.

### Repairnator Nopol files

Nopol is one of the three program repair tools used in order to try to generate a patch for the failing builds. Nopol has been used since February 2017. The file `repairnator.nopol.results` contains information about its execution, such as its configuration and the allocated time. In the following table it is possible to see the number of the branches that contain this file:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.nopol.results                 |                  8.910 |

### Repairnator NPEFix files

NPEFix is the second program repair tool used during the experiment and it has been used since August 2017. In particular, it has been exploited only when a `NullPointerException` has been encountered during the execution of the test suite. In the following table, there are the different files associated with the execution of NPEFix and the number of branches that have these files:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.maven.nperepair.log           |                    921 |
| repairnator.maven.npefix.log              |                    715 |
| repairnator.npefix.results                |                    289 |

* **repairnator.maven.nperepair.log**: it contains Maven log information about the building of a project executed by NPEFix;
* **repairnator.maven.npefix.log**: it contains Maven log information about the execution of the test cases performed by NPEFix;
* **repairnator.npefix.results**: it contains information about the execution of NPEFix, such as if it was successful or not.

### Repairnator Astor files

Astor is the third program repair tool used during the experiment and it has been used starting from September 2017. There are four different files related to the execution of Astor that can be found in the branches, as reported in the following table:

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.astor.log                     |                  6.215 |
| repairnator.astor.mutation.log            |                  4.834 |
| repairnator.astor.results.json            |                  1.083 |
| repairnator.astor.mutation.results.json   |                    945 |

* **repairnator.astor.log**: it contains log information about the execution of Astor, starting from the beginning (e.g., the creation of the [Spoon](http://spoon.gforge.inria.fr) model);
* **repairnator.astor.mutation.log**: it contains log information about the execution of Astor, such as the modification points found with [GZoltar](http://www.gzoltar.com), and the mutation operators applied to these modification points in order to create a possible patch;
* **repairnator.astor.results.json**: it contains the statistics about Astor execution, such as the number of the patches found and the total time used for its execution;
* **repairnator.astor.mutation.results.json**: it contains information about the program variants created by Astor, such as the number of the variants successfully compiled and the number of program variants with compilation errors.

### Repairnator Patch folders

As reported in the following table, there are some branches that also have a folder containing the patches generated by the automatic program repair tools:

| Folder                                    | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator-patches                       |                    120 |
| repairnatorPatches                        |                     52 |

### Types of the collected failing builds

In the following table, we have the number of the builds based on their type:

|                            | only_fail   | failing_passing |     
|----------------------------|:-----------:|:---------------:|
| **Number of the builds**   |      13.387 |             745 |

The type `failing_passing` means that the build failed because at least one test case failed, and the next build was a passing build that did not contain changes in the test files.

### Most common test failure types

Considering the 14.132 collected builds, we analyzed the different failure types occurred. `AssertionError` is the most common failure type with 8.475 occurences observed. The following table shows the top 10 common failure types observed:

| Failure type                         | Occurrences |
|:-------------------------------------|------------:|
| java.lang.AssertionError             |       8.475 |
| java.lang.NullPointerException       |       2.096 |
| org.junit.ComparisonFailure          |       1.291 |
| java.lang.IllegalStateException      |       1.281 |
| java.lang.RuntimeException           |         737 |
| java.lang.Exception                  |         722 |
| java.lang.NoClassDefFoundError       |         442 |
| junit.framework.AssertionFailedError |         437 |
| java.lang.IllegalArgumentException   |         420 |
| java.io.IOException                  |         404 |
| **Other**                            |       2.413 |
| **Total**                            |      18.718 |

On the contrary, taking into account only the custom exceptions, the top 10 common failure types observed are the following ones:

| Failure Type                                               | Occurrences |
|:---------------------------------------------------------- |------------:|
| org.postgresql.util.PSQLException                          |         228 |
| redis.clients.jedis.exceptions.JedisConnectionException    |         173 |
| redis.clients.jedis.exceptions.JedisException              |         163 |
| com.facebook.presto.spi.PrestoException                    |         132 |
| org.springframework.beans.factory.BeanCreationException    |         112 |
| org.openqa.selenium.WebDriverException                     |         105 |
| com.spotify.docker.client.exceptions.DockerException       |          96 |
| spoon.SpoonException                                       |          96 |
| com.mongodb.MongoTimeoutException                          |          95 |
| org.springframework.dao.DataAccessResourceFailureException |          84 |
| **Other**                                                  |       2.594 |
| **Total**                                                  |       3.878 |

Note that in `repairnator.json` files, the are some cases in which the same exception could be reported in two different ways. In particular, considering the values in the array `FailureNames` of JSON object `metrics`, there can be some `repairnator.json` files in which the exception has been saved with the correct name and others in which the exception has been saved with a single `:` at the end of the name. For instance, you can find both `java.lang.NullPointerException` and `java.lang.NullPointerException:`.

The complete list of these exceptions saved both with the correct name and with the symbol `:` at the end of the name is as follows:

* java.lang.NullPointerException;
* java.lang.ExceptionInInitializerError;
* java.lang.AssertionError;
* java.lang.NumberFormatException;
* java.io.FileNotFoundException;
* com.rgross.exception.CountyNotFoundException;
* java.util.concurrent.TimeoutException;
* java.lang.StackOverflowError;
* java.lang.IllegalStateException;
* java.lang.UnsupportedOperationException;
* junit.framework.AssertionFailedError;
* org.opentest4j.AssertionFailedError;
* com.objectia.twostep.exception.APIException;
* java.lang.RuntimeException;
* java.io.EOFException.

### Number of failing test cases per build

During the build process there can be many failing test cases. In the following table it is possible to see the number of the builds based on the number of their failing test cases:

| Number of the failing test cases           | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **0**                                      |                 5.023  |
| **1**                                      |                 3.140  |
| **2**                                      |                 2.957  |
| **3**                                      |                   545  |
| **4**                                      |                   601  |
| **5**                                      |                   177  |
| **6**                                      |                   734  |
| **&gt; 6**                                 |                   955  |

The highest number of the failing test cases observed in a single build has been 804 (`build` [412170129](https://github.com/repairnator/repairnator-experiments/blob/master/builds/412170129.json), `branch` [SeleniumHQ-htmlunit-driver-412170129-20180804-232323](https://github.com/repairnator/repairnator-experiments/tree/SeleniumHQ-htmlunit-driver-412170129-20180804-232323)).

### Number of erroring test cases per build

The number of the builds based on the number of their erroring test cases is reported in the following table:

| Number of the erroring test cases          | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **0**                                      |                 7.082  |
| **1**                                      |                 2.209  |
| **2**                                      |                   733  |
| **3**                                      |                   531  |
| **4**                                      |                   297  |
| **5**                                      |                   233  |
| **6**                                      |                   273  |
| **&gt; 6**                                 |                 2.774  |

The highest number of the erroring test cases observed in a single build has been 14.783 (`build` [376595276
](https://github.com/repairnator/repairnator-experiments/blob/master/builds/376595276.json), `branch` [druid-io-druid-376595276-20180509-013859](https://github.com/repairnator/repairnator-experiments/tree/druid-io-druid-376595276-20180509-013859)).
