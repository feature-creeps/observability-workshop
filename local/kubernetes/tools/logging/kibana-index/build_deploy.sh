#!/usr/bin/env bash
set -euo pipefail

dir="$(dirname "$0")"
namespace="$1"

CLUSTER_NAME=o11y-stack
TAG="local/kibana-index:latest"

docker build "$dir" -t "$TAG"
k3d image import -c "$CLUSTER_NAME" "$TAG"

kubectl apply -n "$namespace" -f "$dir/job.yaml"