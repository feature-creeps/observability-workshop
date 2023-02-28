#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=ingress-nginx

echo "=== Deploy ingress controller in namespace ${NAMESPACE} and create ingresses"

echo "--- install and update helm repos"
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

echo "--- install ingress-nginx"
helm upgrade --install -f "tools/ingress/values.yaml" --wait --namespace "$NAMESPACE" --create-namespace ingress-nginx ingress-nginx/ingress-nginx

echo "--- create ingress rules for olly tools"
kustomize build tools/ingress | kubectl apply -f -
