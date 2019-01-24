FROM openjdk:8-jdk as builder

WORKDIR /imageholder

ARG GRADLE_VERSION=5.1.1

# gradle wrapper
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && mkdir gradle \
    && unzip -d gradle gradle-${GRADLE_VERSION}-bin.zip

# gradle cache
COPY build.gradle .
RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle resolveDependencies

# application
COPY src/ src/
RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle clean build

FROM openjdk:8-jre

COPY --from=builder /imageholder/build/libs/imageholder.jar /
COPY --from=builder /imageholder/src/main/docker/entrypoint.sh /
RUN chmod +x /entrypoint.sh

EXPOSE 8080 5005
ENTRYPOINT ["/entrypoint.sh"]
