FROM appropriate/curl

COPY create-index-patterns.sh /
ENV INDICES="logs- fluent- events- unmatched-"

ENTRYPOINT sh create-index-patterns.sh "$INDICES"