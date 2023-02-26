#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=ingress-nginx

echo "--- install and update helm repos"
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

echo "--- install ingress-nginx"
helm upgrade --install -f "tools/ingress-nginx/values.yaml" --wait --namespace "$NAMESPACE" --create-namespace ingress-nginx ingress-nginx/ingress-nginx

echo "--- create ingress rules for olly tools"
kustomize build tools/istio | kubectl apply -f -
