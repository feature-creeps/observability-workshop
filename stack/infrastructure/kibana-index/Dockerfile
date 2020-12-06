FROM appropriate/curl

COPY create-index-patterns.sh /
COPY create-index-template.sh /
COPY entrypoint.sh /

ENTRYPOINT /entrypoint.sh "$INDICES"