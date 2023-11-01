# Utilizamos una imagen base con Java preinstalado
FROM openjdk:17-jdk-slim

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR construido previamente en la imagen
COPY build/libs/Pizzapp-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
