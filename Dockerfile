FROM openjdk:17
LABEL authors="USER_NAME"
ARG JAR_FILE=build/libs/on-stage-backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]