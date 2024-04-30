# Use a Debian-based OpenJDK image to ensure apt-get is available
FROM openjdk:21-jdk-bullseye AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the Docker image
WORKDIR /app

# Copy your Maven project files into the Docker image
COPY ./ /app/

# Build your application with Maven. This assumes your pom.xml is in the root of your project.
RUN mvn clean package -DskipTests

# Use the same base image for the runtime environment
FROM openjdk:21-jdk-bullseye

# Set the working directory in the Docker image
WORKDIR /app

# Copy the JAR file from the build stage to the run stage
COPY --from=build /app/target/*.jar /app/app.jar

# Command to execute the application
CMD ["java", "-jar", "app.jar"]
