#!/bin/bash
export DB_NAME=$(aws ssm get-parameter --name "/DB/NAME" --query "Parameter.Value" --output text)
export DB_USERNAME=$(aws ssm get-parameter --name "/DB/USERNAME" --query "Parameter.Value" --output text)
export DB_PASSWORD=$(aws ssm get-parameter --name "/DB/PASSWORD" --query "Parameter.Value" --output text)

# Imprimir variables de entorno
echo "DB_NAME: $DB_NAME"
echo "DB_USERNAME: $DB_USERNAME"
echo "DB_PASSWORD: $DB_PASSWORD"

# Eliminar variables de entorno
unset DB_NAME
unset DB_USERNAME
unset DB_PASSWORD

# Verificar que las variables han sido eliminadas
echo "DB_NAME after unset: $DB_NAME"
echo "DB_USERNAME after unset: $DB_USERNAME"
echo "DB_PASSWORD after unset: $DB_PASSWORD"

cd /opt/libros
sudo nohup java -jar \
    -Dserver.port=80 \
    -DB_NAME=$DB_NAME \
    -DB_USERNAME=$DB_USERNAME \
    -DB_PASSWORD=$DB_PASSWORD \
    libros-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &