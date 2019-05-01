#!/bin/sh
JAVA_OPTIONS="-Xms256m -Xmx256m -Xss256k -XX:MaxMetaspaceSize=64m -XX:MaxDirectMemorySize=16m -XX:+UseCompressedOops"
JAVA_OPTIONS="$JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom"
if [ "$JAVA_DEBUG" = true ]; then
  JAVA_OPTIONS="$JAVA_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005";
fi

exec java $JAVA_OPTIONS -jar /imageresize.jar $SPRING_OPTIONS "$@"