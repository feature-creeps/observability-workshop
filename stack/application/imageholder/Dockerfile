FROM openjdk:11-jdk as builder

WORKDIR /service

ARG GRADLE_VERSION=5.1.1

# apm
ARG ELASTIC_APM_AGEN_VERSION=1.16.0
RUN wget https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/${ELASTIC_APM_AGEN_VERSION}/elastic-apm-agent-${ELASTIC_APM_AGEN_VERSION}.jar -O /elastic-apm-agent.jar

# gradle wrapper
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && mkdir gradle \
    && unzip -d gradle gradle-${GRADLE_VERSION}-bin.zip

# gradle cache
COPY build.gradle .
RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle resolveDependencies

# application
COPY src/ src/
RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle build

FROM openjdk:11-jre

COPY --from=builder /elastic-apm-agent.jar /
COPY --from=builder /service/build/libs/imageholder.jar /
COPY --from=builder /service/src/main/docker/entrypoint.sh /
RUN chmod +x /entrypoint.sh

EXPOSE 8080 5005
ENTRYPOINT ["/entrypoint.sh"]
