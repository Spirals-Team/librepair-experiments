# Shop of Han

[![Build Status](https://travis-ci.org/hanfak/ShopOfHan.svg?branch=master)](https://travis-ci.org/hanfak/ShopOfHan)

[![codecov](https://codecov.io/gh/hanfak/ShopOfHan/branch/master/graph/badge.svg)](https://codecov.io/gh/hanfak/ShopOfHan)


[![BCH compliance](https://bettercodehub.com/edge/badge/hanfak/ShopOfHan?branch=master)](https://bettercodehub.com/)

https://bettercodehub.com/

A hanfak.shopofhan.domain driven example of a shop to help learn the technologies used at work.

http://localhost:8081/productscheck?productName=Joy%20Of%20Java


## Running mySql via docker

docker network create --driver=bridge --subnet=192.168.127.0/24 --gateway=192.168.127.1 --ip-range=192.168.127.128/25 mybridge1


From scratch
`docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql`


`docker run --name --net=mybridge mysql -e MYSQL_ROOT_PASSWORD=password -d mysql:tag`

// docker run -it --link some-mysql:mysql --rm mysql sh -c 'exec  mysql -h"172.17.0.3" -P"3306" -uroot -p' 

If already pulled image
`docker start <container name>`

Check containers that are not running
`docker ps -a`


Check if working
`docker ps`

start bash in container
`docker exec -it  <container name>  /bin/bash `

Start mysql
`mysql -u root -p` 

Connect to mysql
`mysql -h"172.17.0.3" -P"3306" -uroot -p `
`mysql -h"127.0.0.1" -P"3306" -uroot -p `

copy and paste sql file (/home/dev/Documents/HAN/ShopOfHan/ShopOfHanSQL/priming.sql)

// TODO: script to run via docker bash


mysql -h localhost -P 3306 --protocol=tcp -uroot -p


## address in use

lsof -i tcp:8081

kill -9 <pid>

#### Hikari db pooling

not working with docker at the momentc

## TODOs

- Delete a product given a product id and rest of the stock
- Update a product given a product id
- Update stock
- Show all products
- Show all products and stock
- Use a test db instead of stub
- use wriemock and 3rd party service
- refactor arg passed to use case to only needed not request
- Validation for unmarshalling
