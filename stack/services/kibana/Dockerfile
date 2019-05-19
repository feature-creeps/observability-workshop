FROM docker.elastic.co/kibana/kibana:6.4.3

COPY kibana.yml /usr/share/kibana/config/

RUN rm -rf /usr/share/kibanaoptimize/bundles
ENV NODE_OPTIONS="--max-old-space-size=4096"

RUN bin/kibana-plugin install https://github.com/pjhampton/kibana-prometheus-exporter/releases/download/6.4.3/kibana-prometheus-exporter-6.4.3.zip
