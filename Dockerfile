From openjdk
ARG JAR_FILE=target/*.jar
COPY . /target/fitnes-0.0.1-SNAPSHOT.jar 

ENTRYPOINT [ "java", "-jar", /app.jar ]