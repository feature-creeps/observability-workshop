#!/usr/bin/env bash
set -eu -o pipefail

# Prereq: login to gcloud cli with `gcloud init`

ZONE="europe-west1-c"
NUM_NODES=3
CLUSTER_NAME="olly-stack"
MACHINE_TYPE="e2-standard-2"

# Setup gke
gcloud container clusters create "$CLUSTER_NAME" --zone "$ZONE" --num-nodes=$NUM_NODES --machine-type="$MACHINE_TYPE"

# WARNING: The Pod address range limits the maximum size of the cluster. Please refer to https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr to learn how to optimize IP address allocation.    

# Get kubeconfig
gcloud container clusters get-credentials "$CLUSTER_NAME" --zone "$ZONE"

