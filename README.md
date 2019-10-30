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

This repository aims to contain code of failing projects.

## Contents
The structure of the repository is as follows:
* Branch `master` contains the `builds` folder and the documentation of this repository;
* The `builds` folder contains a specific JSON file for every Travis CI build failure related to Java programs obtained using the Travis API;
* For each of these failing builds, there is a separated branch with all the information related to the building of a project.

In the subsequent sections we will provide more details about every entry listed above.

### General information
The repository currently contains 14.137 build JSON files in the `builds` folder, each of them associated to a continuous integration (CI) build failure of a Java program that uses Maven as building tool.

The name of these JSON files corresponds to the Travis CI build identifier and their content is the result of the Travis API ```GET /build/{build.id}```.

There are 14.137 different branches (excluding master branch), each of them associated to a build failure. In particular, every branch can have 2, 3 or 4 commits, as reported in the following table:

|                            | 2 commits   | 3 commits   | 4 commits   |
|----------------------------|:-----------:|:-----------:|:-----------:|
| **Number of the branches** | 15          | 13.875      | 247         |

As you can see, most of the branches have 3 commits, where:
* The first one is the failing one;
* The second one contains metadata and log infomation regarding the building of the project;
* The third one, optional, contains a patch developed by an automatic program repair techinque.

The branches that have 4 commits have one commit (the third one) that contains also the human patch.

Every branch associated to a build failure has the file `repairnator.json` that contains relevant information regarding the building of the project.

### Builds Information extracted from the Travis API

Starting from January 2016 to April 2019, we collected the build failures in Travis CI.

A build can have different states, and as reported in the [official documentation of Travis CI](https://docs.travis-ci.com/user/for-beginners/#breaking-the-build), a build is considered broken when one or more of its jobs complete with a state that is not passed: errored, failed, or canceled.

In the following table, we reported the number of the collected builds, divided by their state:

|                            | failed      | passed      | errored     | canceled
|----------------------------|:-----------:|:-----------:|:-----------:|:--------:
| **Number of the builds**   | 13.181      | 896         | 48          | 12

Every build is triggered by an event. In particular, in the following table it is possibile to see the number of the collected builds divided by the type of their trigger event:

|                            | push        | pull request  | cron        | api
|----------------------------|:-----------:|:-------------:|:-----------:|:--------:
| **Number of the builds**   | 7.172       | 6.266         | 539         | 160

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

|                            | only_fail   | failing_passing     
|----------------------------|:-----------:|:---------------:|
| **Number of the builds**   |      13.390 |             747 |



