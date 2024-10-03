
FROM gradle:jdk20 AS build

COPY ./ ./

RUN gradle build -x test


FROM openjdk:20-jdk-slim
COPY --from=build /home/gradle/build/libs/DevOps-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]
