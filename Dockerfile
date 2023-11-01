# Utilizamos una imagen base con Java preinstalado
FROM openjdk:17-jdk-slim as build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos de configuración de Gradle y el wrapper
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Damos permisos de ejecución al script de Gradle
RUN chmod +x gradlew

# Copiamos el código fuente de la aplicación
COPY src src

# Limpiamos el directorio de construcción antes de construir
RUN ./gradlew clean

# Construimos la aplicación (ignorando las pruebas)
RUN ./gradlew build -x test

# Iniciamos una nueva etapa de construcción para la imagen final
FROM openjdk:17-jdk-slim

# Copiamos el JAR construido desde la etapa de construcción
COPY buld/libs/Pizzapp-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app.jar"]

