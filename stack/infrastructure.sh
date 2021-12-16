#!/bin/bash

set -eu -o pipefail

# Prereq: login to gcloud cli with `gcloud init`

ZONE="europe-west1-c"
NUM_NODES=3
CLUSTER_NAME="k8s-olly"
MACHINE_TYPE="e2-standard-4"

# Setup gke
gcloud container clusters create "$CLUSTER_NAME" --zone "$ZONE" --num-nodes=$NUM_NODES --machine-type="$MACHINE_TYPE"

# WARNING: The Pod address range limits the maximum size of the cluster. Please refer to https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr to learn how to optimize IP address allocation.    

# Get kubeconfig
gcloud container clusters get-credentials "$CLUSTER_NAME" --zone "$ZONE"

# install istio
    
istioctl install --set profile=demo -y

kubectl label namespace default istio-injection=enabled
kubectl create clusterrolebinding cluster-admin-binding --clusterrole cluster-admin --user $(gcloud config get-value account)
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/grafana.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/kiali.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/prometheus.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/jaeger.yaml

# install nginx ingress

kubectl create namespace ingress-nginx
kubectl label namespace ingress-nginx istio-injection=enabled       
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.1.0/deploy/static/provider/cloud/deploy.yaml

kubectl create namespace logging
helm repo add elastic https://helm.elastic.co
helm install -n logging elasticsearch elastic/elasticsearch
helm install -n logging kibana elastic/kibana
helm install -n logging filebeat elastic/filebeat
helm install -n logging metricbeat elastic/metricbeat
