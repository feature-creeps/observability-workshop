#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=dima

echo "=== Deploy application stack in namespace ${NAMESPACE}"

echo "--- create dima namespace"
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"
kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite

echo "--- deploy dima"
helm upgrade --install -n "$NAMESPACE" dima application