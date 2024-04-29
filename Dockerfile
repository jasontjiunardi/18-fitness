From openjdk
ARG JAR_FILE=target/*.jar
COPY . /target/fitnes-0.0.1-SNAPSHOT.jar 

ENTRYPOINT [ "java", "-jar", /app.jar ]

# Stage 1: Build the application
FROM maven:4.0.0-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

# Stage 2: Run the application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
