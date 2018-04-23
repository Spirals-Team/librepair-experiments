# valentine-ezugu
#simple transport company order of petrol management for Brest java course(2018)

[![Build Status](https://travis-ci.org/Brest-Java-Course-2018/valentine-ezugu.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2018/valentine-ezugu)
[![Coverage Status](https://coveralls.io/repos/github/Brest-Java-Course-2018/valentine-ezugu/badge.svg)](https://coveralls.io/github/Brest-Java-Course-2018/valentine-ezugu)

# Getting Started
 1. Check  
       
    $java -version  
        
    $export JAVA_HOME = ..
        
    $mvn -version
        
2. Build
    
   from main directory     
   $mvn clean install
   
        
3. Preparing reports
      
        $mvn site
      
        $mvn site:stage
      
        check:/target/stage/index.html
        ``
4.  To run jetty server
        
   To run  rest go to dir* -/rest-producer mvn jetty run 
  
# use embedded jetty server to test rest-producer
     
     mvn -pl rest-producer/ jetty:run
     
     Once started, the REST server should be available at:
     
     http://localhost:8088
     
     Try CURL:
     
     curl -v localhost:8088/trucks
     
     curl -v localhost:8088/trucks/1
     
     curl -H "Content-Type: application/json" -X POST -d '{"truckCode":"BY2354","purchasedDate":"xyz", "description":"my truck is good"}' -v localhost:8088/trucks
     
     curl -X "DELETE" localhost:8088/trucks/1
     
     curl -v localhost:8088/orders/
      
     curl -v  http://localhost:8088/orders?start=2007-01-01&end=2008-01-01

#Angular 

  **what you will need**
    
   **This project was generated with Angular CLI version 1.7.3.**

5.  ng-client app runs on http://localhost:4200/


6.  Travis CI integration
    https://travis-ci.org/Brest-Java-Course-2018/valentine-ezugu/

To be found in this projects are software products of the following versions :

JDK: 1.8

Maven: 3.3.9

Spring: 4

jetty 9.4.8.v20171121

travis ci - also to add jenkins 