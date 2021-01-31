FROM fluent/fluent-bit:1.6

ENV PARSERS_CONF /fluent-bit/etc/parsers_custom.conf
COPY ./conf/parsers_custom.conf $PARSERS_CONF

ENV STREAMS_CONF /fluent-bit/etc/streams_custom.conf
COPY ./conf/streams_custom.conf $STREAMS_CONF

#alternative streams config: /fluent-bit/etc/docker-to-es-streams.conf
COPY ./conf/docker-to-es.conf /fluent-bit/etc/docker-to-es.conf

# docker does not inherit the CMD from the parent image
CMD ["/fluent-bit/bin/fluent-bit", "-c", "/fluent-bit/etc/docker-to-es.conf"]