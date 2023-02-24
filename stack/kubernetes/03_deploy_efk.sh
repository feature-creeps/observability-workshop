#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=logging
ELASTIC_VERSION="7.17.3"

helm repo add elastic https://helm.elastic.co
helm repo add fluent https://fluent.github.io/helm-charts

helm repo update

helm upgrade --install -n "$NAMESPACE" --create-namespace -f tools/efk/elasticsearch.yaml --version ${ELASTIC_VERSION} elasticsearch elastic/elasticsearch
helm upgrade --install -n "$NAMESPACE" -f tools/efk/fluentd.yaml fluentd fluent/fluentd
#  kubectl delete -n "$NAMESPACE" configmaps/kibana-kibana-helm-scripts serviceaccounts/pre-install-kibana-kibana roles/pre-install-kibana-kibana rolebindings.rbac.authorization.k8s.io/pre-install-kibana-kibana jobs.batch/pre-install-kibana-kibana
helm upgrade --install -n "$NAMESPACE" -f tools/efk/kibana.yaml --version ${ELASTIC_VERSION} kibana elastic/kibana
helm upgrade --install -n "$NAMESPACE" --version ${ELASTIC_VERSION} metricbeat elastic/metricbeat
helm upgrade --install -n "$NAMESPACE" --version ${ELASTIC_VERSION} apm-server elastic/apm-server

# apply kibana index mappings and patterns
./tools/efk/kibana-index/build_deploy.sh "$NAMESPACE"