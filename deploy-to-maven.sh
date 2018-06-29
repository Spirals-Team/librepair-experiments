#!/bin/bash

mvn clean

mvn package

mvn source:jar
mvn javadoc:jar

DEPVERSION=$(git describe --tags --abbrev=0 --always HEAD)

mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2 -DrepositoryId=ossrh -DpomFile=pom.xml -Dfile=target/pagarme-java-${DEPVERSION}.jar 
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2 -DrepositoryId=ossrh -DpomFile=pom.xml -Dfile=target/pagarme-java-${DEPVERSION}-sources.jar -Dclassifier=sources
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2 -DrepositoryId=ossrh -DpomFile=pom.xml -Dfile=target/pagarme-java-${DEPVERSION}-javadoc.jar -Dclassifier=javadoc

