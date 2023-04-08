#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=logging
echo "=== Deploy logging components in namespace $NAMESPACE"

echo "--- install and update helm repos"
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

echo "--- deploy loki in namespace $NAMESPACE"
helm upgrade --install -f tools/logging/loki.yaml -n "$NAMESPACE" --create-namespace loki grafana/loki

echo "--- deploy promtail in namespace $NAMESPACE"
helm upgrade --install -f tools/logging/promtail.yaml -n "$NAMESPACE" --create-namespace promtail grafana/promtail