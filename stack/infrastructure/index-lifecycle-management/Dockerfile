FROM appropriate/curl

COPY apply-ilm.sh /
ENV RETENTION_IN_DAYS=5

ENTRYPOINT sh apply-ilm.sh $RETENTION_IN_DAYS