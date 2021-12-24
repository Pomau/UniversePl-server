# Ubuntu
#FROM ubuntu:latest
# Arch
FROM archlinux/archlinux:latest
MAINTAINER Numbers Technologies
LABEL version="1.0"


COPY . /universe

# Ubuntu
# RUN apt-get update && apt-get install -y default-jdk && DEBIAN_FRONTEND=noninteractive \
#     apt-get install -y maven && cd universe && mvn install

# Arch
RUN pacman -Syy —noconfirm —needed -S jdk-openjdk maven  && \
    cd universe && mvn install


ENTRYPOINT ["java", "-jar", "/universe/target/security-0.0.1-SNAPSHOT.jar"]