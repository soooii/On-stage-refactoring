FROM eclipse-temurin:17-jdk-alpine

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} on-stage.jar

ENTRYPOINT ["java","-jar","/on-stage.jar"]
