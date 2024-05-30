#!/bin/bash
export DB_NAME=$(aws ssm get-parameter --name "/DB/NAME" --query "Parameter.Value" --output text)
export DB_USERNAME=$(aws ssm get-parameter --name "/DB/USERNAME" --query "Parameter.Value" --output text)
export DB_PASSWORD=$(aws ssm get-parameter --name "/DB/PASSWORD" --query "Parameter.Value" --output text)

cd /opt/libros
sudo nohup java -jar \
    -Dserver.port=80 \
    -DDB_NAME=$DB_NAME \
    -DDB_USERNAME=$DB_USERNAME \
    -DDB_PASSWORD=$DB_PASSWORD \
    libros-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &