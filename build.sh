#!/bin/bash
source ~/.bash_profile
mvn versions:set -DnewVersion=0.0.2
mvn versions:commit
mvn clean package -DskipTests
scp target/rabbitmq-service-0.0.2.jar  tianji@192.168.1.111:~/project/rabbitmq

