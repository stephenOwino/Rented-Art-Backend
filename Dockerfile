FROM maven:3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/Rented-Art-Backend-0.0.1-SNAPSHOT.jar Rented-Art-Backend.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "Rented-Art-Backend.jar"]

