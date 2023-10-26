#!/bin/bash

BUCKET_NAME=$(aws ssm get-parameter --name "BUCKET_NAME" --query "Parameter.Value" --output text)
DB_PASSWORD=$(aws ssm get-parameter --name "DB_PASSWORD" --query "Parameter.Value" --output text)
DB_URL=$(aws ssm get-parameter --name "DB_URL" --query "Parameter.Value" --output text)
DB_USERNAME=$(aws ssm get-parameter --name "DB_USERNAME" --query "Parameter.Value" --output text)
REDIS_HOST=$(aws ssm get-parameter --name "REDIS_HOST" --query "Parameter.Value" --output text)
S3_KEY=$(aws ssm get-parameter --name "S3_KEY" --query "Parameter.Value" --output text)
SERVER_PORT=$(aws ssm get-parameter --name "SERVER_PORT" --query "Parameter.Value" --output text)
USER_SERVER_URL=$(aws ssm get-parameter --name "USER_SERVER_URL" --query "Parameter.Value" --output text)

# 환경 변수 설정
export BUCKET_NAME=$BUCKET_NAME
export DB_PASSWORD=$DB_PASSWORD
export DB_URL=$DB_URL
export DB_USERNAME=$DB_USERNAME
export REDIS_HOST=$REDIS_HOST
export S3_KEY=$S3_KEY
export SERVER_PORT=$SERVER_PORT
export USER_SERVER_URL=$USER_SERVER_URL
export SPRING_PROFILES_ACTIVE=prod

# 애플리케이션 실행
nohup java -jar /home/ec2-user/content-0.0.1-SNAPSHOT.jar > /dev/null 2>&

