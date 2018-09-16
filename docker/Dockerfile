FROM gradle:jdk10 as builder

COPY --chown=gradle:gradle src /home/gradle/src
COPY --chown=gradle:gradle build.gradle /home/gradle
COPY --chown=gradle:gradle settings.gradle /home/gradle

WORKDIR /home/gradle
RUN gradle build

FROM openjdk:10-jre-slim
EXPOSE 8080
COPY --from=builder /home/gradle/build/libs/bootstrap-service-0.0.1-SNAPSHOT.jar /app/bootstrap-service.jar

WORKDIR /app

CMD java -jar bootstrap-service.jar