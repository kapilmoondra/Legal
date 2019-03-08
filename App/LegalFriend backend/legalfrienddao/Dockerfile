FROM openjdk:8-jre-slim
LABEL maintainer="GlobalLogic"

ARG TERM=xterm
ARG DEBIAN_FRONTEND=noninteractive

ENV MYSQL_USER=root \
    MYSQL_PASSWORD=GLobal@123 \
    MYSQL_HOST=localhost \
    MYSQL_PORT=3306 \
    MYSQL_DATABASE=legalfriend \
    MYSQL_TIMEOUT=30


RUN apt-get update -qq && \
    apt-get install -qq -y --no-install-recommends netcat && \
    rm -rf /var/lib/apt/lists/* && \
    useradd --create-home --comment "java user" java && \
    echo '#!/usr/bin/env bash\n\
    retries=0\n\
    echo -n "Waiting for ${MYSQL_HOST}:${MYSQL_PORT}"\n\
    while ! (nc -w 3 -z ${MYSQL_HOST} ${MYSQL_PORT}); do\n\
    sleep 1\n\
    echo -n "."\n\
    if [ $retries -eq ${MYSQL_TIMEOUT} ]; then\n\
    echo " Timeout, aborting."\n\
    exit 1\n\
    fi\n\
    retries=$(($retries+1))\n\
    done\n\
    java -jar /home/java/app.jar\n' > /entrypoint.sh && \
    chmod 0555 /entrypoint.sh

COPY "target/legalfrienddao-0.0.1-SNAPSHOT.jar" "/tmp/app.jar"
RUN mv /tmp/app.jar /home/java/ && \
    chown -R java:java /home/java && \
    chmod 0500 /home/java/app.jar

USER java
WORKDIR /home/java
EXPOSE 7777

ENTRYPOINT /entrypoint.sh
