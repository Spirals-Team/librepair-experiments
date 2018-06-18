#!/bin/bash

# tenta apagar um container pré-existente. Se não existir, esse comando irá falhar, mas não tem problema
docker rm -f academia-mysql-client-web

docker run -d --network=academia --link academia-mysql:db --name academia-mysql-client-web -p 9000:8080 adminer
