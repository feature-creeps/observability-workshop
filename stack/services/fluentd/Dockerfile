FROM fluent/fluentd:v0.12-debian

RUN ["gem", "install", "fluent-plugin-elasticsearch", "--no-rdoc", "--no-ri", "--version", "1.9.5"]
RUN ["gem", "install", "fluent-plugin-prometheus", "--no-rdoc", "--no-ri", "--version", "0.4.0"]
COPY ./conf/fluent.conf /fluentd/etc/fluent.conf

# docker does not inherit the CMD from the parent image
CMD exec fluentd -c /fluentd/etc/${FLUENTD_CONF} -p /fluentd/plugins $FLUENTD_OPT