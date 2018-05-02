[![Build Status develop](https://travis-ci.org/Respekto/posesor.svg?branch=develop)](https://travis-ci.org/Respekto/posesor/branches)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=posesor&metric=alert_status)](https://sonarcloud.io/dashboard?id=posesor)


# Posesor

#Swagger

Api Documentation

    http://localhost:8080/swagger-ui.html#/payment-controller
    
or url on prod:
    
    http://posesor.net:81/swagger-ui.html#/payment-controller

# Docker-compose

To execute docker-compose script:

    docker-compose up

to stop and remove:

    docker-compose down

to list running images:

    docker-compose ps

#Run profiles
-Dspring.profiles.active=dev

Use maven to compile solution
mvn install

run compiled solution from local docker
docker run -it --rm -p 80:8080 respekto/posesor-ui
open web brower at http://localhost


* to build backend locally please set [TLS off](https://forums.docker.com/t/spotify-docker-maven-plugin-cant-connect-to-localhost-2375/9093/12?u=siudeks) so that spotify plugin can build docker image locally.