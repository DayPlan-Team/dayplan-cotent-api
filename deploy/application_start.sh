#!/bin/bash

BUCKET_NAME=$(aws ssm get-parameter --name "BUCKET_NAME" --query "Parameter.Value" --output text)
DB_PASSWORD=$(aws ssm get-parameter --name "DB_PASSWORD" --query "Parameter.Value" --output text)
DB_URL=$(aws ssm get-parameter --name "DB_URL" --query "Parameter.Value" --output text)
DB_USERNAME=$(aws ssm get-parameter --name "DB_USERNAME" --query "Parameter.Value" --output text)
REDIS_HOST=$(aws ssm get-parameter --name "REDIS_HOST" --query "Parameter.Value" --output text)
S3_KEY=$(aws ssm get-parameter --name "S3_KEY" --query "Parameter.Value" --output text)
USER_SERVER_URL=$(aws ssm get-parameter --name "USER_SERVER_URL" --query "Parameter.Value" --output text)

# 환경 변수 설정
export BUCKET_NAME=$BUCKET_NAME
export DB_PASSWORD=$DB_PASSWORD
export DB_URL=$DB_URL
export DB_USERNAME=$DB_USERNAME
export REDIS_HOST=$REDIS_HOST
export S3_KEY=$S3_KEY
export SERVER_PORT=$SERVER_PORT
export SPRING_PROFILES_ACTIVE=prod

CURRENT_PORT=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/content/health || echo "8081")
if [ $CURRENT_PORT = "8081" ]; then
    NEW_PORT=8080
    OLD_PORT=8081
else
    NEW_PORT=8081
    OLD_PORT=8080
fi

export SERVER_PORT=$NEW_PORT
nohup java -jar /home/ec2-user/content-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

# 헬스 체크 수행
sleep 500
HEALTH_STATUS=$(curl -s http://localhost:$NEW_PORT/content/health | grep '"status": true')
if [ -z "$HEALTH_STATUS" ]; then
    echo "New application health check failed"
    exit 1
fi

sed -i "s/listen $OLD_PORT/listen $NEW_PORT/g" /etc/nginx/sites-available/default
nginx -s reload

OLD_PID=$(pgrep -f "java -jar /home/ec2-user/content-0.0.1-SNAPSHOT.jar --server.port=$OLD_PORT")
if [ -n "$OLD_PID" ]; then
    kill $OLD_PID
fi

