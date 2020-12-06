#!/bin/sh
JAVA_OPTIONS=""
JAVA_OPTIONS="$JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom"

if [ "$APM_ENABLED" = true ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.service_name=imageflip"
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.server_urls=http://apm-server:8200"
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.enable_log_correlation=true"
    JAVA_OPTIONS="$JAVA_OPTIONS -javaagent:/elastic-apm-agent.jar"
    JAVA_OPTIONS="$JAVA_OPTIONS -Delastic.apm.application_packages=com.github.olly.workshop"
fi

if [ "$JAVA_DEBUG" = true ]; then
  JAVA_OPTIONS="$JAVA_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005";
fi

exec java $JAVA_OPTIONS -jar /imageflip.jar $SPRING_OPTIONS "$@"