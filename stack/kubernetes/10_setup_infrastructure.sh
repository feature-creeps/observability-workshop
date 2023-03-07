#!/usr/bin/env bash
set -eu -o pipefail

# Prereq: login to gcloud cli with `gcloud init`

echo "=== setup basic infrastructure"

ZONE="europe-west1-c"
NUM_NODES=3                     # at least 3 nodes are needed for elasticsearch hard affinity settings. Please refer to https://github.com/elastic/helm-charts/blob/main/elasticsearch/README.md
CLUSTER_NAME="olly-stack"
MACHINE_TYPE="e2-standard-2"    # in total at least 8 cpu cores needed

echo "--- setup gke"
gcloud container clusters create "$CLUSTER_NAME" --zone "$ZONE" --num-nodes=$NUM_NODES --machine-type="$MACHINE_TYPE" --disk-size "50GB"

# WARNING: The Pod address range limits the maximum size of the cluster. Please refer to https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr to learn how to optimize IP address allocation.    

echo "--- get kubeconfig"
gcloud container clusters get-credentials "$CLUSTER_NAME" --zone "$ZONE"

