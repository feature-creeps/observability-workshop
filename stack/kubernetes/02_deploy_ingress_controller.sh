#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=ingress-nginx

helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

# todo: verify --wait helps with creating ingress rules below
helm upgrade --install -f "tools/ingress-nginx/values.yaml" --wait --namespace "$NAMESPACE" --create-namespace ingress-nginx ingress-nginx/ingress-nginx

# create ingress rules for olly tools
kustomize build tools/istio | kubectl apply -f -
