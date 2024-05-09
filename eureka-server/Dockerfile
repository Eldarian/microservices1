# build stage
#FROM gradle:7.3.3-jdk17 as build
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build --no-daemon

# production stage
FROM amazoncorretto:17
LABEL authors="eldar"

#COPY --from=build /home/gradle/src/build/libs/eureka-service.jar /app/eureka-service.jar
COPY ./build/libs/eureka-service.jar /app/eureka-service.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "eureka-service.jar"]
EXPOSE 8761
