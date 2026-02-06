FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/awesome-anonymous-forum-0.0.1-SNAPSHOT.jar .
EXPOSE 11050
ENTRYPOINT ["java", "-jar", "awesome-anonymous-forum-0.0.1-SNAPSHOT.jar"]