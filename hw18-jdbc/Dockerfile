FROM amazoncorretto:21.0.8-alpine3.22
WORKDIR /app
COPY build/libs/hw18-jdbc.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
