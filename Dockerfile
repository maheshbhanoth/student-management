# 🏗️ Base image
FROM openjdk:17-jdk-slim

# 🗂️ Create app directory
WORKDIR /app

COPY target/studentmanagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

ENTRYPOINT [ "java", "-jar", "app.jar" ]