#!/bin/bash

# Definir el archivo de log
LOG_FILE="/opt/libros/log/libros_deploy.log"

# Obtener y exportar variables de entorno desde AWS SSM
export DB_NAME=$(aws ssm get-parameter --name "/DB/NAME" --query "Parameter.Value" --output text --with-decryption)
if [ $? -ne 0 ]; then
    echo "Error obteniendo /DB/NAME" | tee -a $LOG_FILE
    exit 1
fi

export DB_USERNAME=$(aws ssm get-parameter --name "/DB/USERNAME" --query "Parameter.Value" --output text --with-decryption)
if [ $? -ne 0 ]; then
    echo "Error obteniendo /DB/USERNAME" | tee -a $LOG_FILE
    exit 1
fi

export DB_PASSWORD=$(aws ssm get-parameter --name "/DB/PASSWORD" --query "Parameter.Value" --output text --with-decryption)
if [ $? -ne 0 ]; then
    echo "Error obteniendo /DB/PASSWORD" | tee -a $LOG_FILE
    exit 1
fi

# Imprimir variables de entorno
echo "DB_NAME: $DB_NAME" | tee -a $LOG_FILE
echo "DB_USERNAME: $DB_USERNAME" | tee -a $LOG_FILE
echo "DB_PASSWORD: $DB_PASSWORD" | tee -a $LOG_FILE

# Eliminar variables de entorno
unset DB_NAME
unset DB_USERNAME
unset DB_PASSWORD

# Verificar que las variables han sido eliminadas
echo "DB_NAME after unset: $DB_NAME" | tee -a $LOG_FILE
echo "DB_USERNAME after unset: $DB_USERNAME" | tee -a $LOG_FILE
echo "DB_PASSWORD after unset: $DB_PASSWORD" | tee -a $LOG_FILE

# Cambiar a directorio de la aplicación
cd /opt/libros

# Iniciar la aplicación Java con nohup y redirigir salida a log
sudo nohup java -jar \
    -Dserver.port=80 \
    -DB_NAME=$DB_NAME \
    -DB_USERNAME=$DB_USERNAME \
    -DB_PASSWORD=$DB_PASSWORD \
    libros-0.0.1-SNAPSHOT.jar >> $LOG_FILE 2>&1 &