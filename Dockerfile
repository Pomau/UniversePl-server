FROM ubuntu:latest
MAINTAINER Numbers Technologies
LABEL version="1.0"

RUN apt-get update && \
    apt-get update && \
    apt install openjdk-11-jdk -y

COPY . /universe



