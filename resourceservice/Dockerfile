# Build stage
FROM gradle:7.3.3-jdk17 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Run stage

FROM amazoncorretto:17
LABEL authors="eldar"

COPY ./build/libs/resource-service.jar /app/resource-service.jar

WORKDIR /app

CMD ["java", "-jar", "resource-service.jar"]
EXPOSE 8080
