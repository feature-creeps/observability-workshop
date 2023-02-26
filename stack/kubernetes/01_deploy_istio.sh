#!/usr/bin/env bash
set -eu -o pipefail

NAMESPACE=istio-system

echo "--- install istio"
# minimal profile does not deploy egress/ingress, see https://istio.io/latest/docs/setup/additional-setup/config-profiles/
istioctl install --set profile=minimal -y

kubectl create clusterrolebinding cluster-admin-binding --clusterrole cluster-admin --user "$(gcloud config get-value account)" || true

echo "--- install and update helm repos"
helm repo add kiali https://kiali.org/helm-charts
helm repo update

# kiali
echo "--- install kiali"
helm upgrade --install -f "tools/kiali/values.yaml" --namespace "$NAMESPACE" kiali-server kiali/kiali-server

echo "--- install jaeger"
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/jaeger.yaml