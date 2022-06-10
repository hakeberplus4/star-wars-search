FROM bellsoft/liberica-openjdk-alpine:latest as builder
WORKDIR /src
COPY gradle /src/gradle
COPY build.gradle gradlew gradlew.bat settings.gradle /src/
RUN ./gradlew dependencies
COPY src src
# Build it
RUN ./gradlew assemble

