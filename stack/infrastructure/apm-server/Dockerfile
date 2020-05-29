FROM docker.elastic.co/apm/apm-server:7.7.0
COPY apm-server.yml /usr/share/apm-server/apm-server.yml
USER root
RUN chmod go-w /usr/share/apm-server/apm-server.yml
USER apm-server