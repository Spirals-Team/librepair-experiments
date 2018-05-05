# How to run this script from same folder:
# docker build .

FROM ubuntu:16.04
MAINTAINER Chinmay Kolhatkar <chinmay@apache.org>
MAINTAINER Thomas Weise <thw@apache.org>

COPY app/ /app/
RUN /app/setup.sh

USER apex
WORKDIR /home/apex
EXPOSE 50070 8088

ENTRYPOINT ["/app/init.sh"]

