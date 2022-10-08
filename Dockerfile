FROM gradle:7-jdk18-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :backend:app:shadowJar --no-daemon

FROM openjdk:19-alpine
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/backend/app/build/libs/*.jar /app/songbook.jar
# Mount application.conf into /app/application.conf
ENTRYPOINT ["java", "-jar", "/app/songbook.jar", "-config=/app/application.conf"]
