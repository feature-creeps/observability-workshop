#!/usr/bin/env bash
set -euo pipefail

NAMESPACE=ingress
echo "=== Deploy ingress controller in namespace $NAMESPACE"

echo "--- install and update helm repos"
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

echo "--- install nginx ingress"
helm upgrade --install nginx-ingress ingress-nginx/ingress-nginx \
    --namespace "$NAMESPACE" \
    --set controller.replicaCount=1 \
    --create-namespace \
    --wait

kubectl apply -f "infrastructure/service-ingress.yaml"