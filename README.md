# repairnator-experiments

This is an open-science repository whith data related to ["How to Design a Program Repair Bot? Insights from the Repairnator Project"](https://hal.inria.fr/hal-01691496/document) (ICSE 2018) and [Repairnator patches programs automatically](https://ubiquity.acm.org/article.cfm?id=3349589), (Ubiquity, Association for Computing Machinery, 2019)

```
@inproceedings{repairnator,
 title = {{How to Design a Program Repair Bot? Insights from the Repairnator Project}},
 author = {Urli, Simon and Yu, Zhongxing and Seinturier, Lionel and Monperrus, Martin},
 booktitle = {{Proceedings of the 40th International Conference on Software Engineering}},
 year = {2018},
}
```

This repository aims to contain the code of the failing projects and also information about the bugs reproduction, test failures and repair attempt logs using three different repair tools: [NPEFix](https://github.com/SpoonLabs/npefix), [Nopol](https://github.com/SpoonLabs/nopol), and [Astor](https://github.com/SpoonLabs/astor).

## Contents
The structure of the repository is as follows:
* Branch `master` contains the `builds` folder and the documentation of this repository;
* The `builds` folder contains a specific JSON file obtained using the Travis API for every Travis CI build failure related to Java programs that use Maven as building tool;
* For each of these failing builds, there is a specific branch with all the information related to the building of a project, bug reproduction, test failures and repair attempt logs.

In the subsequent sections we will provide more details about every entry listed above.

## General information
The repository currently contains 14.137 build JSON files in the `builds` folder, each of them associated to a Continuous Integration (CI) build failure of a Java program that uses Maven as building tool.

The name of these JSON files corresponds to the Travis CI build identifier and their content is the result of the Travis API V3 ```GET /build/{build.id}```. More details can be found on the [official documentation](https://docs.travis-ci.com/user/developer/#api-v3).

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

## Builds Information extracted from the Travis API

Starting from January 2016 to April 2019, we collected the build failures in Travis CI.

A build can have different states, and as reported in the [official documentation of Travis CI](https://docs.travis-ci.com/user/for-beginners/#breaking-the-build), a build is considered broken when one or more of its jobs complete with a state that is not passed: errored, failed, or canceled.

In the following table, we reported the number of the collected builds, divided by their state:

|                            | failed      | passed      | errored     | canceled  |
|----------------------------|:-----------:|:-----------:|:-----------:|:---------:|
| **Number of the builds**   | 13.181      | 896         | 48          | 12        |

Every build is triggered by an event. In particular, in the following table it is possibile to see the number of the collected builds divided by the type of their trigger event:

|                            | push        | pull request  | cron        | api      |
|----------------------------|:-----------:|:-------------:|:-----------:|:--------:|
| **Number of the builds**   | 7.172       | 6.266         | 539         | 160      |

Using the information extracted from the Travis API, we also divided the builds based on the build duration expressed in seconds. The following table shows the number of the builds divided by different build time ranges:

|                                            | Number of the builds   |
|:------------------------------------------:|-----------------------:|
| **0 &lt; build_time &le; 100**             |                 1.527  |
| **100 &lt; build_time &le; 500**           |                 3.950  |
| **500 &lt; build_time &le; 1.000**         |                 1.443  |
| **1.000 &lt; build_time &le; 5.000**       |                 3.400  |
| **5.000 &lt; build_time &le; 10.000**      |                 1.220  |
| **10.000 &lt; build_time &le; 25.000**     |                 2.172  |
| **25.000 &lt; build_time &le; 50.000**     |                   408  |
| **build_time &gt; 50.000**                 |                    17  |

The minimum build time recorded has been 3 seconds (`build` [357685462](https://github.com/repairnator/repairnator-experiments/blob/master/builds/357685462.json), `branch` [as0kir-topjava-357685462-20180324-053259](https://github.com/repairnator/repairnator-experiments/tree/as0kir-topjava-357685462-20180324-053259)), while the maximum build time recorded has been 60.035 seconds (`build` [247511890](https://github.com/repairnator/repairnator-experiments/blob/master/builds/247511890.json), `branch` [apache-flink-247511890-20170627-234202_bugonly](https://github.com/repairnator/repairnator-experiments/tree/apache-flink-247511890-20170627-234202_bugonly)).

### Builds Information extracted using Repairnator

Every branch is linked to a build JSON file in `master` branch and every JSON file is related to a Travis CI build failure.
In particular, in addition to the program files, every branch has some files with the keyword `repairnator` in their name in which there are useful information about the build process, such as the number and the types of the failures occurred.

The following tables show the different types of `repairnator` files that can be found in a branch and the number of the branches that have these files:

#### Repairnator Data Files

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.json                          |                  14137 |
| repairnator-patches                       |                    120 |
| repairnatorPatches                        |                     52 |
| repairnator                               |                     10 |

Every branch has its proper `repairnator.json` file in which where are the following information:

#### Repairnator Maven Log Files

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.maven.buildproject.log        |                  12860 |
| repairnator.maven.testproject.log         |                  12860 |
| repairnator.maven.computeclasspath.log    |                  12804 |
| repairnator.maven.resolvedependency.log   |                   7845 |

#### Repairnator Nopol Files

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.nopol.results                 |                   8914 |

#### Repairnator Astor Files

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.astor.log                     |                   6218 |
| repairnator.astor.mutation.log            |                   4835 |
| repairnator.astor.results.json            |                   1083 |
| repairnator.astor.mutation.results.json   |                    945 |

#### Repairnator NPEFix Files

| File                                      | Number of the branches |
|:------------------------------------------|-----------------------:|
| repairnator.maven.nperepair.log           |                    922 |
| repairnator.maven.npefix.log              |                    715 |
| repairnator.npefix.results                |                    289 |

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
| java.lang.AssertionError                      |                 7280 |
| java.lang.NullPointerException                |                 1836 |
| org.junit.ComparisonFailure                   |                 1293 |
| java.lang.IllegalStateException               |                 1280 |
| java.lang.AssertionError                      |                 1198 |
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
