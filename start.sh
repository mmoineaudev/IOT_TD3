#!/bin/bash

cd java_mqtt/td3moineau ; 
mvn clean package ; 
java -jar target/td3-moineau-1.0-SNAPSHOT.jar ; 
