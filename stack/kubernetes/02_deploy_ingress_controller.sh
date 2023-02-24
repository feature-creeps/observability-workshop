#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=ingress-nginx

# todo: verify --wait helps with creating ingress rules below
helm upgrade --install --repo https://kubernetes.github.io/ingress-nginx --namespace "$NAMESPACE" --create-namespace --set controller.service.loadBalancerIP=34.140.57.146 ingress-nginx ingress-nginx

# create ingress rules for olly tools
kustomize build tools/istio | kubectl apply -f -
