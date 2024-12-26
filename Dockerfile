FROM gradle:8.12.0-jdk21-alpine AS build
ADD --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :backend:app:shadowJar --no-daemon

# Should use JRE and slim variant of Debian (or Oracle Linux)
# Java 21+ required, higher is better as it has new security fixes
FROM openjdk:24-slim
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/backend/app/build/libs/app-all.jar /app/app.jar
COPY --from=build /home/gradle/src/backend/application.conf /app/application.conf
# Mount application.conf into /app/application.conf
ENTRYPOINT ["java", "-jar", "/app/app.jar", "-config=/app/application.conf"]
