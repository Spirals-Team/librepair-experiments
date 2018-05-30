FROM postgres
MAINTAINER Shcherbinin Ilya scherbininiliy@gmail.com

RUN apt-get update
RUN apt-get -y install default-jre 

COPY  target/docker_test_app.jar /application/
COPY  target/sql/init_test_env.sql /docker-entrypoint-initdb.d/

WORKDIR /application/

EXPOSE 8090