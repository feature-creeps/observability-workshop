FROM openjdk:11-jdk as builder

WORKDIR /build

ARG SERVICE
ARG GRADLE_VERSION=5.1.1

# apm
ARG ELASTIC_APM_AGEN_VERSION=1.16.0
RUN wget https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/${ELASTIC_APM_AGEN_VERSION}/elastic-apm-agent-${ELASTIC_APM_AGEN_VERSION}.jar -O /elastic-apm-agent.jar

# gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && mkdir gradle \
    && unzip -d gradle gradle-${GRADLE_VERSION}-bin.zip

## gradle cache
#COPY build.gradle .
#COPY settings.gradle .
#RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle resolveDependencies
#
## libs
#COPY springevents springevents
#RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle springevents:build
#
## service build
#COPY $SERVICE/src/ $SERVICE/src/
#RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle $SERVICE:build

COPY build.gradle .
COPY settings.gradle .
COPY springevents springevents
COPY $SERVICE $SERVICE

RUN ./gradle/gradle-${GRADLE_VERSION}/bin/gradle $SERVICE:build

FROM openjdk:11-jre

ARG SERVICE

COPY --from=builder /elastic-apm-agent.jar /
COPY --from=builder /build/$SERVICE/build/libs/$SERVICE.jar /
COPY entrypoint.sh /

RUN chmod +x /entrypoint.sh

ENV SERVICE=$SERVICE
RUN echo "service: $SERVICE"

EXPOSE 8080 5005
ENTRYPOINT /entrypoint.sh $SERVICE
