#!/usr/bin/env bash
set -eu -o pipefail

# todo: why use profile demo?
istioctl install --set profile=demo -y

kubectl create clusterrolebinding cluster-admin-binding --clusterrole cluster-admin --user $(gcloud config get-value account)

# grafana
kubectl apply -f tools/istio/istio-grafana.yaml

# kiali
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/kiali.yaml

# prometheus
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/prometheus.yaml

# jaeger
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/jaeger.yaml
