#!/bin/sh

docker rm -f academia-client-web-dev
docker rm -f academia-client-web-prod
docker build . -f scripts/Dockerfile-dev -t academia-client-web-dev

# se o seu SO não suporta $(pwd), substitua o comando $(pwd) pelo caminho até a raíz da pasta `academia-client-web`
docker run -it -p 3000:3000 --network=academia -v $(pwd):/app --name academia-client-web-dev academia-client-web-dev
