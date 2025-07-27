FROM openjdk:21-bookworm

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar


EXPOSE 11050
ENTRYPOINT ["java", "-jar","-Dtrust_all_cert=true","/app.jar"]