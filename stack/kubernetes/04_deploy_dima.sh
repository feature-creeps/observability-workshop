#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=dima

# create dima namespace
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"
kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite

# deploy application services & traffic gen
kustomize build application | kubectl -n "$NAMESPACE" apply -f -