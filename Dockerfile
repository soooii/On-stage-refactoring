FROM openjdk:17
LABEL authors="USER_NAME"
ARG JAR_FILE=build/libs/on-stage-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-Dspring.profiles.active=deploy", "-jar", "/docker-springboot.jar", ">", "app.log"]