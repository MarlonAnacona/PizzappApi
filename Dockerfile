# Utilizamos una imagen base con Java preinstalado
FROM openjdk:17-jdk-slim

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR construido previamente y los archivos de keystore y certificado en la imagen
COPY build/libs/Pizzapp-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/keystore.p12 /app
COPY src/main/resources/springboothttps.crt /app

# Exponemos el puerto para HTTPS
EXPOSE 8081

# Comando para ejecutar la aplicación con las propiedades del keystore
CMD ["java", "-jar", "-Dserver.port=8081", "-Dserver.ssl.key-store=file:/app/keystore.p12", "-Dserver.ssl.key-store-password=pizzapp", "-Dserver.ssl.keyStoreType=PKCS12", "-Dserver.ssl.keyAlias=springboothttps", "app.jar"]
