#!/bin/bash

# download the latest docker iDempiere pgSQL image and run it
# on Travis we need to run the docker image on port 5433 (there is standard PostgreSQL already running)
if [ ! -f idempiere-docker-pgsql-5.1.0.latest.tar.gz ]; then
    wget https://sourceforge.net/projects/idempiereksys/files/idempiere-docker-image/idempiere-docker-pgsql-5.1.0.latest.tar.gz
fi
zcat idempiere-docker-pgsql-5.1.0.latest.tar.gz | docker load
docker volume create --name idempiere-pgsql-datastore
docker run -d -v idempiere-pgsql-datastore:/data -p 5433:5432 -e PASS="postgres" idempiere/idempiere-docker-pgsql:5.1.0.latest
