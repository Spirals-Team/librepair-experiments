var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":47,"id":20,"methods":[{"el":15,"sc":5,"sl":10},{"el":22,"sc":5,"sl":17},{"el":28,"sc":5,"sl":24},{"el":34,"sc":5,"sl":30},{"el":40,"sc":5,"sl":36},{"el":46,"sc":5,"sl":42}],"name":"TestSuiteExample2","sl":6}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_0":{"methods":[{"sl":42}],"name":"test2","pass":true,"statements":[{"sl":44},{"sl":45}]},"test_1":{"methods":[{"sl":17}],"name":"test4","pass":true,"statements":[{"sl":19},{"sl":20},{"sl":21}]},"test_10":{"methods":[{"sl":30}],"name":"test8","pass":true,"statements":[{"sl":32},{"sl":33}]},"test_4":{"methods":[{"sl":10}],"name":"test3","pass":true,"statements":[{"sl":12},{"sl":13},{"sl":14}]},"test_5":{"methods":[{"sl":36}],"name":"test9","pass":true,"statements":[{"sl":38},{"sl":39}]},"test_6":{"methods":[{"sl":24}],"name":"test7","pass":true,"statements":[{"sl":26},{"sl":27}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [4], [], [4], [4], [4], [], [], [1], [], [1], [1], [1], [], [], [6], [], [6], [6], [], [], [10], [], [10], [10], [], [], [5], [], [5], [5], [], [], [0], [], [0], [0], [], [], []]
