#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=monitoring

helm repo add grafana https://grafana.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

# prometheus
helm upgrade --install -f "tools/prometheus/values.yaml" -n "$NAMESPACE" --create-namespace prometheus prometheus-community/prometheus

# node exporter
helm upgrade --install -f "tools/node-exporter/values.yaml" -n "$NAMESPACE" --create-namespace node-exporter prometheus-community/prometheus-node-exporter

# kube state metrics
helm upgrade --install -f "tools/kube-state-metrics/values.yaml" -n "$NAMESPACE" --create-namespace kube-state-metrics prometheus-community/kube-state-metrics

# grafana
helm upgrade --install -f "tools/grafana/values.yaml" -n "$NAMESPACE" --create-namespace grafana grafana/grafana

# kiali
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/kiali.yaml

# jaeger
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/jaeger.yaml

kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite

