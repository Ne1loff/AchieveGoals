FROM gradle:jdk17 as build

ENV BUILDER_HOME=/home/builder
RUN useradd -s /bin/bash -m -d $BUILDER_HOME builder
USER builder
WORKDIR $BUILDER_HOME

COPY build.gradle.kts ./
COPY settings.gradle.kts ./
COPY gradle ./
COPY gradlew ./

RUN ./grad
COPY src ./src
RUN mvn package -DpgTestsPath=/home/builder/

FROM openjdk:16-alpine3.13
WORKDIR /app
COPY --from=build /home/builder/target/backend.jar ./
RUN mkdir config
EXPOSE 8080
ENTRYPOINT ["java", "-DsettingsDir=/app/config/", "-jar", "/app/backend.jar"]