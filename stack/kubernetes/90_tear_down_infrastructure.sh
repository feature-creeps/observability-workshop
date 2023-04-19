#!/usr/bin/env bash
set -eu -o pipefail

ZONE="europe-west1-c"
CLUSTER_NAME="olly-stack"

echo "=== Tear down infrastructure and thus all stacks"

echo "--- delete cluster"
gcloud container clusters delete "$CLUSTER_NAME" --zone "$ZONE"

