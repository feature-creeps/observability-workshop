FROM gradle:7.6-jdk17-alpine AS builder

WORKDIR /build

ARG ELASTIC_APM_AGENT_VERSION=1.36.0

# apm
RUN wget https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/${ELASTIC_APM_AGENT_VERSION}/elastic-apm-agent-${ELASTIC_APM_AGENT_VERSION}.jar -O /elastic-apm-agent.jar

# gradle cache
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
RUN gradle resolveDependencies

# libs
COPY springevents springevents
RUN gradle springevents:build

# service build
ARG SERVICE
COPY $SERVICE $SERVICE

RUN gradle $SERVICE:build

FROM eclipse-temurin:17.0.6_10-jre AS final
ARG SERVICE

COPY --from=builder /elastic-apm-agent.jar /
COPY --from=builder /build/$SERVICE/build/libs/$SERVICE.jar /
COPY entrypoint.sh /

RUN chmod +x /entrypoint.sh

ENV SERVICE=$SERVICE

EXPOSE 8080 5005
ENTRYPOINT /entrypoint.sh $SERVICE
