# Hekate.io

Java Library for Cluster Communications and Computing.

[![Build Status](https://travis-ci.org/hekate-io/hekate.svg?branch=master)](https://travis-ci.org/hekate-io/hekate)
[![codecov](https://codecov.io/gh/hekate-io/hekate/branch/master/graph/badge.svg)](https://codecov.io/gh/hekate-io/hekate)
[![Javadocs](http://javadoc.io/badge/io.hekate/hekate-all.svg)](http://javadoc.io/doc/io.hekate/hekate-all)

## License
Open source [Apache License v2.0](http://www.apache.org/licenses/)  

## Features

- **Cluster**
    - Gossip-based Decentralized Cluster Membership
    - Pluggable Bootstrapping (aka Seed Node Discovery)
        - Multicast
        - JDBC
        - Shared File System
        - Clouds (based on [Apache JClouds](http://jclouds.apache.org))
            - Amazon EC2/S3
            - Azure Blob Storage
            - Google Cloud Storage
            - etc
        - [ZooKeeper](https://zookeeper.apache.org) (_planned_) 
        - [Etcd](https://github.com/coreos/etcd) (_planned_)
    - Cluster Event Listeners    
    - User-defined Properties and Roles of Cluster Nodes
    - Cluster Views and Node Filtering API
    
- **Messaging**
    - Synchronous and Asynchronous ([Netty](http://netty.io)-based) APIs
    - Cluster-aware Load Balancing and Routing
    - SSL/TLS Encryption of Network Communications (optional)
    - Back Pressure Policies
    - Pluggable Serialization
        - [Kryo](https://github.com/EsotericSoftware/kryo)
        - [FST](https://github.com/RuedigerMoeller/fast-serialization)
        - JDK Serialization
        - Manual Serialization
        
- **Remote Procedure Calls (RPC)**
    - Type-safe Invocation of Remote Java objects
    - Automatic Discovery and Load Balancing
    - Synchronous and Asynchronous APIs
    - Multi-node Broadcasting and Aggregation of Results
    - Pluggable Failover and Retry Policies
    - ...and everything from the "Messaging" section above:)
    
- **Cluster Leader Election (aka Cluster Singleton)**
    - Decentralized Leader Election
    - Followers are Aware of the Current Leader
    - Leader can Dynamically Yield Leadership

- **Distributed Locks**
    - Synchronous and Asynchronous Reentrant Locks
    - Decentralized Lock Management
    - Configurable Lock Groups (aka Lock Regions)

- **Distributed Metrics**
    - Custom Metrics (User-defined Counters and Probes)
    - Cluster-wide (Nodes can See Metrics of Other Nodes)
    - Metrics-based Load Balancing (_planned_)
    - Recording and Analysis
        - [StatsD](https://github.com/etsy/statsd)
        - [InfluxDB](https://www.influxdata.com/time-series-platform/influxdb/)
        - [CloudWatch](https://aws.amazon.com/cloudwatch/) 
                
- **Spring Framework/Boot Support (optional)**
    - Spring-compliant Beans
    - [Spring XML Schema](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/xsd-configuration.html) 
      to Simplify Configuration
    - [Spring Boot](https://projects.spring.io/spring-boot) Auto-configurations and @Annotations

- **Raft-based Replicated State Machines (_planned_)**


## Documentation

For now, the most detailed documentation is provided as part of [javadocs](http://javadoc.io/doc/io.hekate/hekate-all/). 
Complete reference guide is coming soon.

## Code Examples

Please see [hekate-io/hekate-examples](https://github.com/hekate-io/hekate-examples) project.

## Maven artifacts

 * For projects based on **Spring Boot**:
```
<dependency>
    <groupId>io.hekate</groupId>
    <artifactId>hekate-spring-boot</artifactId>
    <version>2.4.0-SNAPSHOT</version>
</dependency>
```

 * For projects based on **Spring Framework**:
```
<dependency>
    <groupId>io.hekate</groupId>
    <artifactId>hekate-spring</artifactId>
    <version>2.4.0-SNAPSHOT</version>
</dependency>
```

 * For standalone applications:
```
<dependency>
    <groupId>io.hekate</groupId>
    <artifactId>hekate-core</artifactId>
    <version>2.4.0-SNAPSHOT</version>
</dependency>
```

 * Other artifacts:
    - [hekate-jclouds-core](hekate-jclouds-core/) - Integration with the [Apache JClouds](http://jclouds.apache.org) 
      for cloud environments.
    - [hekate-jclouds-aws](hekate-jclouds-aws/) - Extended integration with the [Amazon EC2](https://aws.amazon.com) cloud.
    - [hekate-metrics-influxdb](hekate-metrics-influxdb/) - Metrics publishing to the [InfluxDB](https://www.influxdata.com) 
      (time-series data storage).
    - [hekate-metrics-statsd](hekate-metrics-statsd/) - Metrics publishing to the [StatsD](https://github.com/etsy/statsd) 
      (statistics aggregation daemon). 

## How to build

### Software requirements:

 - Latest stable [Oracle JDK 8](http://www.oracle.com/technetwork/java/) or [Open JDK 8](http://openjdk.java.net/)
 - Latest stable [Docker](https://www.docker.com) (required for tests only)


### Building (no tests):

 - `cd` to the project's root folder
 - run `./mvnw clean package -DskipTests=true`
 
### Building (with tests):
 
  - cd to the project's root folder
  - make a copy of `test.properties` file with name `my_test.properties`
  - edit `my_test.properties` according to your environment
  - run `docker-compose up -d` to prepare Docker-based testing infrastructure
  - run `./mvnw clean package`
  
## Release notes

### v.2.3.1 (18-May-2018)

 New Features and Improvements:
 
 - Added support for SSL/TLS configuration using Spring Boot application properties.
 - Removed @ConditionalOnClass from Spring Boot auto-configurations in order to simplify early detection of configuration errors.

 Bug fixes:
 
 - Fixed a concurrency issue that could lead to premature failure of message routing logic without applying a failover policy.

 Dependency Upgrades:
 
 - Upgraded to Netty 4.1.25.Final.
 - Upgraded to AWS SDK 1.11.331.
 - Upgraded to InfluxDB-Java 2.10.

### v.2.3.0 (4-May-2018)

 New Features and Improvements:
 
 - Added ClusterView#awaitForNodes() method - simplifies waiting for the conditions of cluster topology.
 - Added Added ClusterNode#isRemote() method - simplifies lambda-based filtering of cluster topology.
 - Added LockRegion#cluster() method - cluster view of a lock region.

### v.2.2.2 (21-Apr-2018)

 Bug fixes:
 
 - Fixed a memory leak when trying to write to a closed network channel.

 Dependency Upgrades:

 - Upgraded to Netty 4.1.21.Final.

### v.2.2.1 (12-Apr-2018)

 Bug fixes:
 
 - Fixed deadlock during MulticastSeedNodeProvider termination.

### v.2.2.0 (11-Apr-2018)

 New Features and Improvements:
 
 - Metrics publishing to Amazon CloudWatch (see [CloudWatchMetricsPlugin](https://static.javadoc.io/io.hekate/hekate-all/2.3.0/index.html?io/hekate/metrics/cloudwatch/CloudWatchMetricsPlugin.html)).
 - Better logging of seed node discovery events.

 Dependency Upgrades:
 
 - Upgraded to Kryo 4.0.2.
 - Upgraded to Spring Framework 4.3.16.RELEASE.
 - Upgraded to Spring Boot 1.5.12.RELEASE.
 - Upgraded to Apache JClouds 2.1.0.
 - Upgraded to AWS SDK 1.11.294.

### v.2.1.0 (22-Feb-2018)

 New features and improvements:
 
 - JMX support by main services and components.
 - JDBC-based Split-brain detection 
   (see [JdbcConnectivityDetector](https://static.javadoc.io/io.hekate/hekate-all/2.3.0/index.html?io/hekate/cluster/split/JdbcConnectivityDetector.html)).
 - Added a configuration option for JVM termination in case a node considers itself in Split-brain state
   (see [SplitBrainAction](https://static.javadoc.io/io.hekate/hekate-all/2.3.0/index.html?io/hekate/cluster/split/SplitBrainAction.html)).
 - Optimized timeouts processing in the messaging service (expired messages are ignored when received).
 - Added [TimerMetric](https://static.javadoc.io/io.hekate/hekate-all/2.3.0/index.html?io/hekate/metrics/local/TimerMetric.html).

 Dependency upgrades:
 
 - Upgraded to Netty 4.1.21.Final.
 - Upgraded to FST 2.57. 

### v.2.0.0 (2-Jan-2018)

 New features and improvements:
 
 - Remote Procedure Call (RPC) service for Java objects 
   (see [RpcService](https://static.javadoc.io/io.hekate/hekate-all/2.3.0/index.html?io/hekate/rpc/RpcService.html)).
 - Added 'deferredJoin' property to Spring bootstrap in order to control the timing of joining the cluster. 
 - Moved load balancing APIs to the `io.hekate.messaging.loadbalance` package.
 - Moved Netty-based implementation of networking APIs to public packages.
 - Merged Kryo and FST codecs into the `hekate-core` module (dropped `hekate-kryo` and `hekate-fst` modules).
 - Dropped Task Service (replaced by the RPC service). 
 
 Dependency upgrades:
 
 - Upgraded to Netty 4.1.19.Final.
 - Upgraded to Spring Framework 4.3.13.RELEASE. 
 - Upgraded to Spring Boot 1.5.9.RELEASE. 
 - Upgraded to AWS SDK 1.11.213.
 - Upgraded to FST 2.52. 
 - Upgraded to InfluxDB-Java 2.7. 

### v.1.0.2 (23-Sep-2017)

 New features and improvements:
 
 - Implemented support for intercepting and transforming inbound/outbound messages 
   (see [MessageInterceptor](https://static.javadoc.io/io.hekate/hekate-all/2.3.0/index.html?io/hekate/messaging/MessageInterceptor.html)).
   
 Dependency upgrades:
 
 - Upgraded to Netty 4.1.15.Final.
 - Upgraded to Kryo 4.0.1.
 - Upgraded to Apache jClouds 2.0.2.

### v.1.0.1 (01-Jul-2017)

 New features and improvements:

 - Fixed invalid links in javadocs.
 - Fixed invalid URL in `<scm>` section of pom.xml files.  
 
 Dependency upgrades:
 
 - Upgraded to Spring Boot 1.5.4.RELEASE.
 - Upgraded to AWS Java SDK 1.11.158.
 - Upgraded to Spring Framework 4.3.9.RELEASE.

### v.1.0.0 (30-Jun-2017)

 - Initial version.  
