#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=logging
ELASTIC_VERSION="7.17.3"

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

echo "--- apply index-lifecycle-management"
./tools/logging/index-lifecycle-management/build_deploy.sh "$NAMESPACE"

echo "--- install curator"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/curator.yaml elasticsearch-curator lebenitza/elasticsearch-curator

echo "--- install fluentd"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/fluentd.yaml fluentd fluent/fluentd
#  kubectl delete -n "$NAMESPACE" configmaps/kibana-kibana-helm-scripts serviceaccounts/pre-install-kibana-kibana roles/pre-install-kibana-kibana rolebindings.rbac.authorization.k8s.io/pre-install-kibana-kibana jobs.batch/pre-install-kibana-kibana

echo "--- install kibana"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/kibana.yaml --version ${ELASTIC_VERSION} kibana elastic/kibana

echo "--- install metricbeat"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/metricbeat.yaml --version ${ELASTIC_VERSION} metricbeat elastic/metricbeat

echo "--- install apm-server"
helm upgrade --install -n "$NAMESPACE" --version ${ELASTIC_VERSION} apm-server elastic/apm-server

echo "--- install heartbeat"
kubectl apply -f tools/logging/heartbeat-v7.17.yaml

echo "--- apply kibana index mappings and patterns"
./tools/logging/kibana-index/build_deploy.sh "$NAMESPACE"