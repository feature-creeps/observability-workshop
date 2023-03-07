#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE="cert-manager"
VERSION="v1.6.1"

echo "=== Deploy cert-manager in namespace ${NAMESPACE}"

helm repo add jetstack https://charts.jetstack.io
helm repo update

helm upgrade --install \
  cert-manager jetstack/cert-manager \
  --namespace "$NAMESPACE" \
  --create-namespace \
  --version "$VERSION" \
  --set installCRDs=true

kubectl apply -f tools/cert-manager/letsencrypt_prod.yaml -n "$NAMESPACE"