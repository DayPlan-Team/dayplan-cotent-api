#!/bin/bash

# 임시 정보
export DB_URL=jdbc:mysql://localhost:3306/dayplancontentlocal;
export DB_PASSWORD=;
export DB_USERNAME=root;
export REDIS_HOST=localhost;
export S3_KEY=test;
export BUCKET_NAME=test;
export USER_SERVER_URL=http://localhost:8080;
export SERVER_PORT=8081;

java -jar -Dspring.profiles.active=prod ../build/libs/content-0.0.1-SNAPSHOT.jar
