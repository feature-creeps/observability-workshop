FROM fluent/fluentd:v0.12-debian

RUN ["gem", "install", "fluent-plugin-elasticsearch", "--no-rdoc", "--no-ri", "--version", "1.9.5"]
RUN ["gem", "install", "fluent-plugin-prometheus", "--no-rdoc", "--no-ri", "--version", "0.4.0"]
COPY ./fluent.conf /fluentd/etc/fluent.conf
