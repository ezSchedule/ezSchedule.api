FROM openjdk:17-jdk-alpine

RUN apk update

RUN apk add --no-cache maven

COPY api-schedule/target /app

WORKDIR /app

#CMD ["java", "-jar", "api-eschedule-0.0.1-SNAPSHOT.jar"]