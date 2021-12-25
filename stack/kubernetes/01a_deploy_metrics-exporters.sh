#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=monitoring

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

# node exporter
helm upgrade --install -f "tools/node-exporter/values.yaml" -n "$NAMESPACE" --create-namespace node-exporter prometheus-community/prometheus-node-exporter

# kube state metrics
helm upgrade --install -f "tools/kube-state-metrics/values.yaml" -n "$NAMESPACE" --create-namespace kube-state-metrics prometheus-community/kube-state-metrics

kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite
