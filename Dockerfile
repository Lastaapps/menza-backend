FROM gradle:7-jdk18 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :backend:app:shadowJar --no-daemon

FROM openjdk:18
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/backend/app/build/libs/app-all.jar /app/app.jar
COPY --from=build /home/gradle/src/backend/application.conf /app/application.conf
# Mount application.conf into /app/application.conf
ENTRYPOINT ["java", "-jar", "/app/app.jar", "-config=/app/application.conf"]
