FROM openjdk:11-jdk as builder

WORKDIR /service

ARG GRADLE_VERSION=5.1.1

# gradle wrapper
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && mkdir gradle \
    && unzip -d gradle gradle-${GRADLE_VERSION}-bin.zip

# gradle cache
COPY build.gradle .

# application
COPY src/ src/
RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle bootJar

FROM openjdk:11-jre

COPY --from=builder /service/build/libs/imagegrayscale.jar /app/imagegrayscale.jar

EXPOSE 8080 5005

ENTRYPOINT ["java"]
CMD ["-jar", "/app/imagegrayscale.jar"]
