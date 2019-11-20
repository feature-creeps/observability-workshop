FROM grafana/grafana:6.1.6
ADD ./provisioning /etc/grafana/provisioning
ADD ./config.ini /etc/grafana/config.ini
ADD ./dashboards /var/lib/grafana/dashboards
RUN grafana-cli plugins install raintank-worldping-app
RUN grafana-cli plugins install jdbranham-diagram-panel