#!/bin/bash

docker run -d -v academia-mysql-data:/var/lib/mysql --name academia-mysql --network=academia -p 3306:3306 -e MYSQL_ROOT_PASSWORD=academia -e MYSQL_DATABASE=academia -e MYSQL_USER=academia -e MYSQL_PASSWORD=academia mysql:5.7.21
