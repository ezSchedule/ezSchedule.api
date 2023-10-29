FROM openjdk:17-jdk-alpine

RUN apk update

RUN apk add --no-cache maven

COPY api-schedule/ /app

WORKDIR /app

EXPOSE 8443

ENTRYPOINT ["java","-jar","api-schedule.jar"]
