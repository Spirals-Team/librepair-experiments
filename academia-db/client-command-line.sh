#!/bin/bash

# tenta apagar um container pré-existente. Se não existir, esse comando irá falhar, mas não tem problema
docker rm -f academia-mysql-client

# usuário e senha são "academia"
# nas opções abaixo, -hacademia-mysql seta o host do server como academia-mysql, e -uacademia seta o usuário como academia
docker run -it --rm --network=academia --name academia-mysql-client mysql:5.7.21 mysql -hacademia-mysql -uacademia -p
