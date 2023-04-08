#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=monitoring

echo "=== Deploy monitoring stack in namespace ${NAMESPACE}"

echo "--- install and update helm repos"
helm repo add grafana https://grafana.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

echo "--- install prometheus"
helm upgrade --install -f "tools/monitoring/prometheus.yaml" -n "$NAMESPACE" --create-namespace prometheus prometheus-community/prometheus

echo "--- install grafana"
helm upgrade --install -f "tools/monitoring/grafana.yaml" -n "$NAMESPACE" --create-namespace grafana grafana/grafana

echo "--- create grafana dashboards through configmaps"
kubectl apply -f "../../stack/kubernetes/tools/monitoring/dashboards"
