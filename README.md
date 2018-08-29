# 项目简介

本项目用于优化写作体验。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/charimg/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/charimg)
[![Build Status](https://www.travis-ci.org/houbb/charimg.svg?branch=master)](https://www.travis-ci.org/houbb/charimg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/houbb/charimg/badge.svg?branch=master)](https://coveralls.io/github/houbb/charimg?branch=master)

# 变更日志

> [变更日志](doc/CHANGELOG.md)

# 快速开始

## 引入

```
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>charming-core</artifactId>
    <version>${mvn.version}</version>
</dependency>
```

## 使用

- 数字的修正例子

```
/**
*
* Method: charming(String string)
*
*/
@Test
public void charmingTest() throws Exception {
    CharmingResult result = new MyCharming().charimg("我的3天啊12 岁,15 斤");
    Assert.assertEquals("我的 3 天啊 12 岁, 15 斤", result.getResult());
}
```



