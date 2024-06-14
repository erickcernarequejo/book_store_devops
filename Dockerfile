FROM amazoncorretto:21-alpine3.16-jdk

# Establecer la zona horaria
ENV TZ='America/Lima'

# Variables de entorno para la base de datos
ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Crear un directorio de trabajo para la aplicación
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/*.jar /app/libros.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "libros.jar"]