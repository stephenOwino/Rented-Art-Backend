FROM maven:3-eclipse-temurin-17 AS build
COPY. .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/*.jar Rented-Art-Backend.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar", "Rented-Art-Backend.jar"]