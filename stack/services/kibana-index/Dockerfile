FROM appropriate/curl

COPY create-index-patterns.sh /
ENV INDICES="log- mgmt- events- unmatched-"

ENTRYPOINT sh create-index-patterns.sh "$INDICES"