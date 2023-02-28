#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=dima

echo "=== Deploy application stack in namespace ${NAMESPACE}"

echo "--- create dima namespace"
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"
kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite

echo "--- deploy application services & traffic gen"
kubectl delete job -n "$NAMESPACE" --ignore-not-found traffic-gen-traffic-gen
kustomize build application | kubectl -n "$NAMESPACE" apply -f -