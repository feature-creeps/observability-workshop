#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=logging
ELASTIC_VERSION="7.17.3"
FLUENTD_VERSION="0.4.3"

echo "=== Deploy logging stack in namespace ${NAMESPACE}"

echo "--- install and update helm repos"
helm repo add elastic https://helm.elastic.co
helm repo add fluent https://fluent.github.io/helm-charts
helm repo add lebenitza https://lebenitza.github.io/charts
helm repo update

echo "--- install elasticsearch"
helm upgrade --install -n "$NAMESPACE" --create-namespace -f tools/logging/elasticsearch.yaml --version ${ELASTIC_VERSION} elasticsearch elastic/elasticsearch

echo "--- apply prometheus-elasticsearch-exporter"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/prometheus-elasticsearch-exporter.yaml prometheus-elasticsearch-exporter prometheus-community/prometheus-elasticsearch-exporter

echo "--- install curator"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/curator.yaml elasticsearch-curator lebenitza/elasticsearch-curator

echo "--- install fluentd"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/fluentd.yaml fluentd fluent/fluentd --version ${FLUENTD_VERSION}

echo "--- install kibana"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/kibana.yaml --version ${ELASTIC_VERSION} kibana elastic/kibana

echo "--- install apm-server"
helm upgrade --install -n "$NAMESPACE" --version ${ELASTIC_VERSION} apm-server elastic/apm-server