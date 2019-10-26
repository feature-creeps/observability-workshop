FROM appropriate/curl

COPY create-index-patterns.sh /
ENV INDICES="logs- fluent- apm- zipkin-dependency- zipkin-span- unmatched-"

ENTRYPOINT sh create-index-patterns.sh "$INDICES"