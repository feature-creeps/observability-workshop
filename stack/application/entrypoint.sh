#!/bin/sh

SERVICE=$1

JAVA_OPTIONS=""
JAVA_OPTIONS="$JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom"

if [ "$APM_ENABLED" = true ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.service_name=$SERVICE"
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.server_urls=http://apm-server-apm-server.logging.svc.cluster.local:8200"
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.enable_log_correlation=true"
    JAVA_OPTIONS="$JAVA_OPTIONS -javaagent:/elastic-apm-agent.jar"
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.application_packages=com.github.olly.workshop"
fi

if [ "$JAVA_DEBUG" = true ]; then
  JAVA_OPTIONS="$JAVA_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005";
fi

exec java $JAVA_OPTIONS -jar /$SERVICE.jar $SPRING_OPTIONS "$@"