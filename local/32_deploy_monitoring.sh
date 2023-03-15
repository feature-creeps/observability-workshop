#!/usr/bin/env bash
set -euo pipefail

echo "=== Deploy monitoring components"

NAMESPACE=monitoring
echo "--- deploy grafana tempo in namespace $NAMESPACE"
helm upgrade --install -n "$NAMESPACE" -f ../stack/kubernetes/tools/local/tempo.yaml --create-namespace tempo grafana/tempo-distributed


NAMESPACE=logging
echo "--- deploy grafana loki in namespace $NAMESPACE"
helm upgrade --install -f ../stack/kubernetes/tools/local/loki.yaml -n "$NAMESPACE" --create-namespace loki grafana/loki
echo "--- deploy promtail in namespace $NAMESPACE"
helm upgrade --install -f ../stack/kubernetes/tools/local/promtail.yaml -n "$NAMESPACE" --create-namespace promtail grafana/promtail


NAMESPACE=monitoring
echo "--- deploy grafana in namespace $NAMESPACE"
helm upgrade --install -f ../stack/kubernetes/tools/local/grafana.yaml -n "$NAMESPACE" --create-namespace grafana grafana/grafana