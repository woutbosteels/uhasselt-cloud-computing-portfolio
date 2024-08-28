FROM  maven:3.9-amazoncorretto-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /home/app/target/kwik-beheer-0.0.1-SNAPSHOT.jar /usr/local/lib/spring-boot-app.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/spring-boot-app.jar"]
