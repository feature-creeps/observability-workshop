#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=dima
IMAGE_TAG=${1:-"latest"}

echo "=== Deploy application stack with image tag ${IMAGE_TAG} in namespace ${NAMESPACE}"

if [[ "$IMAGE_TAG" == "latest" ]]; then
  echo "!!! Warning: Do not use latest for workshop preparation, can cause issues with unavailable references!"
fi

echo "--- create dima namespace"
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"
kubectl label namespace "$NAMESPACE" istio-injection=enabled --overwrite

echo "--- deploy dima"
helm upgrade --install -n "$NAMESPACE" --set global.image.tag=$IMAGE_TAG dima application

