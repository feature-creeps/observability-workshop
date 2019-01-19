FROM openjdk:8-jre

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh
COPY imageholder.jar /

EXPOSE 8080 5005
ENTRYPOINT ["/entrypoint.sh"]
