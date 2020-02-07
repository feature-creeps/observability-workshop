FROM appropriate/curl

COPY create-index-patterns.sh /
COPY create-index-template.sh /
ENV INDICES="logs-"

ENTRYPOINT sh create-index-template.sh && sh create-index-patterns.sh "$INDICES"