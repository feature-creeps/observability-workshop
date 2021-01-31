FROM fluent/fluent-bit:1.6

ENV PARSERS_CONF /fluent-bit/etc/parsers_custom.conf
COPY ./conf/parsers_custom.conf $PARSERS_CONF

ENV STREAMS_CONF /fluent-bit/etc/streams_custom.conf
COPY ./conf/streams_custom.conf $STREAMS_CONF

ENV FLUENTBIT_CONF /fluent-bit/etc/docker-to-es.conf
#ENV FLUENTBIT_CONF /fluent-bit/etc/docker-to-es-streams.conf
COPY ./conf/docker-to-es.conf $FLUENTBIT_CONF

# docker does not inherit the CMD from the parent image
CMD exec /fluent-bit/bin/fluent-bit -c $FLUENTBIT_CONF