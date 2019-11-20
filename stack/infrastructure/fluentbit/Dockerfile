FROM fluent/fluent-bit:0.14.4

ENV PARSERS_CONF /fluent-bit/etc/parsers_custom.conf
COPY ./conf/parsers_custom.conf $PARSERS_CONF

ENV FLUENTBIT_CONF /fluent-bit/etc/docker-to-es.conf
COPY ./conf/docker-to-es.conf $FLUENTBIT_CONF

# docker does not inherit the CMD from the parent image
CMD exec /fluent-bit/bin/fluent-bit -c $FLUENTBIT_CONF