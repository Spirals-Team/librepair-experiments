if does not container exists 
then download container
else
    get container name store
        docker ps -aqf "name=mysql"
    start container
        docker start <container name>
    start bash
        docker exec -it 953834d124c6 /bin/bash
    start mysql server
        mysql -h"172.17.0.3" -P"3306" -uroot -ppassword
    run sql script
    exit
    
DB_USER='root'
DB_PASS='password'
DB='shop_of_han_database'
    
containerName=`docker ps -aqf "name=mysql"`
docker start $containerName
docker exec -it $containerName /bin/bash
mysql -h"172.17.0.3" -P"3306" -uroot -p
expect "password: "
password