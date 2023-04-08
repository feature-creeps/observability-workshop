#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=monitoring
echo "=== Deploy tracing components in namespace $NAMESPACE"

echo "--- install and update helm repos"
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

echo "--- deploy tempo"
helm upgrade --install -n "$NAMESPACE" -f tools/tracing/tempo.yaml --create-namespace tempo grafana/tempo-distributed
