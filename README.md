[![Build Status](https://travis-ci.org/Brest-Java-Course-2018/Kavaliuk-Pavel.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2018/Kavaliuk-Pavel)

[![Coverage Status](https://coveralls.io/repos/github/Brest-Java-Course-2018/Kavaliuk-Pavel/badge.svg?branch=master)](https://coveralls.io/github/Brest-Java-Course-2018/Kavaliuk-Pavel?branch=master)
   
    jdk 1.8
    Apache Maven 3.3.9
    OS "Linux"

1. Check  
    
    $java -version  
    
    $export JAVA_HOME = ...
    
    $mvn -version
    
2. Build

    
    $mvn clean install
    
3. Preparing reports
  
    $mvn site
  
    $mvn site:stage
  
    check: ``<project>/target/stage/index.html``
     
     