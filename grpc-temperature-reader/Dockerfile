FROM  maven:3.9-amazoncorretto-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/grpc-temperature-reader-1.0-SNAPSHOT-jar-with-dependencies.jar /app/grpc-temperature-reader.jar
CMD ["java", "-jar", "/app/grpc-temperature-reader.jar"]

EXPOSE 9090

# Run the jar file
CMD ["java", "-jar", "grpc-temperature-reader.jar"]
