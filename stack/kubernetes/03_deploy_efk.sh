#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=logging

helm repo add elastic https://helm.elastic.co
helm repo add fluent https://fluent.github.io/helm-charts

helm repo update

helm upgrade --install -n "$NAMESPACE" --create-namespace -f tools/efk/elasticsearch.yaml elasticsearch elastic/elasticsearch
helm upgrade --install -n "$NAMESPACE" -f tools/efk/fluentd.yaml fluentd fluent/fluentd
helm upgrade --install -n "$NAMESPACE" -f tools/efk/kibana.yaml kibana elastic/kibana
helm upgrade --install -n "$NAMESPACE" metricbeat elastic/metricbeat

