var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":45,"id":0,"methods":[{"el":13,"sc":5,"sl":8},{"el":20,"sc":5,"sl":15},{"el":26,"sc":5,"sl":22},{"el":32,"sc":5,"sl":28},{"el":38,"sc":5,"sl":34},{"el":44,"sc":5,"sl":40}],"name":"TestSuiteExample","sl":6}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_0":{"methods":[{"sl":28}],"name":"test8","pass":true,"statements":[{"sl":30},{"sl":31}]},"test_1":{"methods":[{"sl":40}],"name":"test2","pass":true,"statements":[{"sl":42},{"sl":43}]},"test_11":{"methods":[{"sl":22}],"name":"test7","pass":true,"statements":[{"sl":24},{"sl":25}]},"test_2":{"methods":[{"sl":15}],"name":"test4","pass":true,"statements":[{"sl":17},{"sl":18},{"sl":19}]},"test_4":{"methods":[{"sl":34}],"name":"test9","pass":true,"statements":[{"sl":36},{"sl":37}]},"test_5":{"methods":[{"sl":8}],"name":"test3","pass":true,"statements":[{"sl":10},{"sl":11},{"sl":12}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [5], [], [5], [5], [5], [], [], [2], [], [2], [2], [2], [], [], [11], [], [11], [11], [], [], [0], [], [0], [0], [], [], [4], [], [4], [4], [], [], [1], [], [1], [1], [], [], []]
