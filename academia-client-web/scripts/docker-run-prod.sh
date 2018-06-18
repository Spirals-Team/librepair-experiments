#!/bin/sh

docker rm -f academia-client-web-prod
docker run -d -p 3000:80 --network=academia --name academia-client-web-prod academia-client-web-prod
