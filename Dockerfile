FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
# Asumiendo que tu archivo .jar se llama 'miaplicacion.jar', ajusta el nombre según sea necesario
COPY build/libs/Pizzapp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
