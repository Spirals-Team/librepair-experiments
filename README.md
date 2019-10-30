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

### Build Information

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




