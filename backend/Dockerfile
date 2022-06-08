FROM gradle:jdk17 as build
ENV BUILDER_HOME=/home/builder
RUN useradd -s /bin/bash -m -d $BUILDER_HOME builder
USER builder
WORKDIR $BUILDER_HOME

COPY gradle ./gradle
COPY gradlew ./
COPY gradlew.bat ./
COPY settings.gradle.kts ./

COPY build.gradle.kts ./

COPY src ./src

RUN ./gradlew build -x test

FROM openjdk:16-alpine3.13

WORKDIR /app

COPY --from=build /home/builder/build/libs/achieve_goals-0.0.1-SNAPSHOT.jar ./
RUN mkdir config

EXPOSE 8080
ENTRYPOINT ["java", "-DsettingsDir=/app/config/", "-jar", "/app/achieve_goals-0.0.1-SNAPSHOT.jar"]