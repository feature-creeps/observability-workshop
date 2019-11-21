FROM appropriate/curl

COPY create-index-patterns.sh /
COPY create-index-template.sh /
ENV INDICES="logs- fluent- apm- zipkin-dependency- zipkin-span- unmatched-"

ENTRYPOINT sh create-index-template.sh && sh create-index-patterns.sh "$INDICES"