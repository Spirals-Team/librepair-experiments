# LiveLib crawler v1.2.0

[![Build Status](https://travis-ci.org/spoluyan/livelib-crawler.svg?branch=master)](https://travis-ci.org/spoluyan/livelib-crawler)
[![Coverage Status](https://coveralls.io/repos/github/spoluyan/livelib-crawler/badge.svg)](https://coveralls.io/github/spoluyan/livelib-crawler)
[![License](http://img.shields.io/:license-mit-blue.svg)](http://doge.mit-license.org)

## Usage

Add to your `pom.xml`

```
<repositories>
  <repository>
    <id>oss.sonatype.org</id>
    <url>https://oss.sonatype.org/content/repositories/staging</url>
  </repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>pw.spn</groupId>
    <artifactId>livelib-crawler</artifactId>
    <version>1.2.0</version>
  </dependency>
</dependencies>
```

Main logic is implemented in `pw.spn.crawler.livelib.LivelibCrawler` class. Just create new instance or inject into your service to use it.

## Current implemented operations

* Get wish to read books by username
* Get reading books by username
* Get already read books by username
* Get unread read books by username
* Search for books
