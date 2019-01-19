FROM openjdk:8-jdk as builder

WORKDIR /app

# gradle wrapper
RUN wget https://services.gradle.org/distributions/gradle-2.14.1-bin.zip \
    && mkdir gradle \
    && unzip -d gradle gradle-2.14.1-bin.zip

# gradle cache
COPY build.gradle .
RUN ./gradle/gradle-2.14.1/bin/gradle resolveDependencies

# application
COPY src/ src/
RUN ./gradle/gradle-2.14.1/bin/gradle clean build

FROM openjdk:8-jre

COPY --from=0 /app/build/libs/imageholder.jar /
COPY --from=0 /app/src/main/docker/entrypoint.sh /
RUN chmod +x /entrypoint.sh

EXPOSE 8080 5005
ENTRYPOINT ["/entrypoint.sh"]
