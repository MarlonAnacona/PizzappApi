# Usar una imagen base de JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Crear un directorio donde la aplicación residirá
WORKDIR /app

# Copiar el archivo JAR construido por Gradle al contenedor
COPY build/libs/Pizzapp-0.0.1-SNAPSHOT.jar app.jar

# Informar al Docker que la aplicación se ejecutará en el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar"]
