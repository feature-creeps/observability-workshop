#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=ingress-nginx

kubectl create namespace "$NAMESPACE"
kubectl label namespace "$NAMESPACE" istio-injection=enabled

# todo: verify --wait helps with creating ingress rules below
kubectl apply --wait -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.1.0/deploy/static/provider/cloud/deploy.yaml

# create ingress rules for olly tools
kustomize build tools/istio | kubectl -n istio-system apply -f -
