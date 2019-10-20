FROM docker.elastic.co/kibana/kibana:7.3.1

COPY kibana.yml /usr/share/kibana/config/

RUN rm -rf /usr/share/kibanaoptimize/bundles
ENV NODE_OPTIONS="--max-old-space-size=4096"

RUN bin/kibana-plugin install https://github.com/pjhampton/kibana-prometheus-exporter/releases/download/7.3.1/kibana-prometheus-exporter-7.3.1.zip
