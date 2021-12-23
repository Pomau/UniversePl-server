FROM ubuntu:latest
MAINTAINER Numbers Technologies
LABEL version="1.0"

RUN apt-get update && apt-get install -y default-jdk && DEBIAN_FRONTEND=noninteractive \
    apt-get install -y maven

COPY . /universe

RUN cd universe && mvn install
ENTRYPOINT ["java", "-jar", "/universe/target/security-0.0.1-SNAPSHOT.jar"]



