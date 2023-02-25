#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=monitoring

helm repo add grafana https://grafana.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

# prometheus
helm upgrade --install -f "tools/prometheus/values.yaml" -n "$NAMESPACE" --create-namespace prometheus prometheus-community/prometheus

# grafana
helm upgrade --install -f "tools/grafana/values.yaml" -n "$NAMESPACE" --create-namespace grafana grafana/grafana

# jaeger
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/jaeger.yaml

kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite

