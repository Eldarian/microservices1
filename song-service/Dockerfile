FROM amazoncorretto:17
LABEL authors="eldar"

COPY ./build/libs/song-service.jar /app/song-service.jar

WORKDIR /app

CMD ["java", "-jar", "song-service.jar"]

EXPOSE 8081