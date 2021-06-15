#!/bin/bash

set -eu -o pipefail

# Prereq: login to gcloud cli with `gcloud init`

REGION="europe-west3"
CLUSTER_NAME="k8s-olly"

# Setup gke
gcloud container clusters create-auto "$CLUSTER_NAME" \
    --region "$REGION"

# WARNING: The Pod address range limits the maximum size of the cluster. Please refer to https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr to learn how to optimize IP address allocation.    

# Get kubeconfig
gcloud container clusters get-credentials "$CLUSTER_NAME" --region "$REGION"
