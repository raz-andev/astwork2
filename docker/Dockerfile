FROM openjdk:17
LABEL authors="ultra"

ARG APP_JAR=*.jar

WORKDIR /app

COPY ${APP_JAR} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080