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
* `builds` folder contains all the Travis build identifiers, and the JSON file contains the result of the Travis API eg <https://api.travis-ci.org/v3/build/348887356> 
* branch `master` contains the documentation of this repo
* each branch corresponds to a failing build in Java
  * each branch has a file `repairnator.json` containing metadata
  * each branch has log files `*.log` of the Maven process
  * each branch has 2 or 3 commits, where:
    * the first commi is the failing one
    * the second one contains metadata and log information
    * the  last one, optional, contains a patch (human or generated? TBD)
