#!/usr/bin/env bash
set -euo pipefail

REGISTRY=local
NAMESPACE=dima
IMAGE_TAG="$(git rev-parse --short HEAD)-snapshot"

echo "=== Deploy application stack with image tag ${IMAGE_TAG} in namespace ${NAMESPACE}"

echo "--- import images into k3d"
for app in frontend imageorchestrator imageholder imagerotator imagegrayscale imageresize imageflip imagethumbnail trafficgen; do
    kind load docker-image "$REGISTRY/dima-$app:$IMAGE_TAG"
done

echo "--- create dima namespace"
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"

echo "--- deploy dima"
helm upgrade --install -f ../../stack/kubernetes/application/values_local.yaml -n "$NAMESPACE" \
    --set global.image.tag="$IMAGE_TAG" \
    dima ../../stack/kubernetes/application