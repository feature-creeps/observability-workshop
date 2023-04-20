#!/usr/bin/env bash
set -euo pipefail

REGISTRY=local
NAMESPACE=dima
IMAGE_TAG="$(git rev-parse --short HEAD)-snapshot"
CLUSTER_NAME=o11y-stack

echo "=== Deploy application stack with image tag ${IMAGE_TAG} in namespace ${NAMESPACE}"

echo "--- import images into k3d"
for app in frontend imageorchestrator imageholder imagerotator imagegrayscale imageresize imageflip imagethumbnail trafficgen; do
    k3d image import -c "$CLUSTER_NAME" "$REGISTRY/dima-$app:$IMAGE_TAG"
done

echo "--- create dima namespace"
kubectl get namespace "$NAMESPACE" || kubectl create namespace "$NAMESPACE"

echo "--- deploy dima"
helm upgrade --install -f ../application/values.yaml -n "$NAMESPACE" \
    --set global.image.tag="$IMAGE_TAG" \
    --set global.tls.enabled=false \
    dima ../../stack/kubernetes/application