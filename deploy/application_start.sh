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
export USER_SERVER_URL=$USER_SERVER_URL
export SPRING_PROFILES_ACTIVE=prod

HEALTH_STATUS_8080=$(curl -s http://localhost:8080/content/health | grep '{"status":true}')

if [ -z "$HEALTH_STATUS_8080" ]; then
    # 8080 상태가 0이라면, 해당 포트 사용 안하는 중
    NEW_PORT=8080
    OLD_PORT=8081
else
    NEW_PORT=8081
    OLD_PORT=8080
fi

echo "new port: $NEW_PORT, old port: $OLD_PORT"

export SERVER_PORT=$NEW_PORT
nohup java -jar /home/ec2-user/content-0.0.1-SNAPSHOT.jar &

# 헬스 체크 수행
echo "health check!!!"

sleep 50
HEALTH_STATUS=$(curl -s http://localhost:$NEW_PORT/content/health | grep '{"status":true}')
if [ -z "$HEALTH_STATUS" ]; then
  echo "health_status = $HEALTH_STATUS"
  echo "New application health check failed"
  exit 1
fi

echo "old port: $OLD_PORT remove"

OLD_PID=$(pgrep -f "java -jar /home/ec2-user/content-0.0.1-SNAPSHOT.jar --server.port=$OLD_PORT")
if [ -n "$OLD_PID" ]; then
    kill $OLD_PID
fi

HEALTH_STATUS=$(curl -s http://localhost:8080/content/health | grep '{"status":true}')
if [ -z "$HEALTH_STATUS" ]; then
        echo "port running: 8081"
        IDLE_PORT=8081
else
        echo "port running: 8080"
        IDLE_PORT=8080
fi

echo "switch port: $IDLE_PORT"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Nginx Reload"
sudo service nginx reload