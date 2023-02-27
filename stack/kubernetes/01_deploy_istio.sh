#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=istio-system

echo "--- install and update helm repos"
helm repo add istio https://istio-release.storage.googleapis.com/charts
helm repo add kiali https://kiali.org/helm-charts
helm repo update

echo "--- install istio"
helm upgrade --install -n "$NAMESPACE" --create-namespace istio-base istio/base -n istio-system
helm upgrade --install -n "$NAMESPACE" --create-namespace istiod istio/istiod -n istio-system --wait

kubectl create clusterrolebinding cluster-admin-binding --clusterrole cluster-admin --user "$(gcloud config get-value account)" || true

# kiali
echo "--- install kiali"
helm upgrade --install -f "tools/kiali/values.yaml" --namespace "$NAMESPACE" kiali-server kiali/kiali-server

echo "--- install jaeger"
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/jaeger.yaml