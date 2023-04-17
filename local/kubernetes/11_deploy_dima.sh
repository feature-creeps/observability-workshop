#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=dima
IMAGE_TAG="$(git rev-parse --short HEAD)-snapshot"

echo "=== Deploy application stack with image tag ${IMAGE_TAG} in namespace ${NAMESPACE}"

echo "--- create dima namespace"
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"

echo "--- deploy dima"
helm upgrade --install -f ../application/values.yaml -n "$NAMESPACE" \
    --set global.image.tag="$IMAGE_TAG" \
    --set global.tls.enabled=false \
    dima ../../stack/kubernetes/application