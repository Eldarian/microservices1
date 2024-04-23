FROM amazoncorretto:17
LABEL authors="eldar"

COPY ./build/libs/resource-service.jar /app/resource-service.jar

WORKDIR /app

CMD ["java", "-jar", "resource-service.jar"]
EXPOSE 8080
