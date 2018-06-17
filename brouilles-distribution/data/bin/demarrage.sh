#!/bin/bash
java -jar -Xmx1024m -Dspring.profiles.active=prod -Dlogging.config=conf/logback.xml -Djasypt.encryptor.password=supersecretz jars/brouilles-boot-${project.version}.jar