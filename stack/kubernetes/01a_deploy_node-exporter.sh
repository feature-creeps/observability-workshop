#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=monitoring

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

helm upgrade --install -f "tools/node-exporter/values.yaml" -n "$NAMESPACE" --create-namespace node-exporter prometheus-community/prometheus-node-exporter

kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite
