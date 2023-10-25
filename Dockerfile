FROM openjdk:17-jdk-alpine

RUN apk update

RUN apk add --no-cache maven

COPY api-schedule/target /app

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java","-jar","api-schedule-0.0.1-SNAPSHOT-new.jar"]
