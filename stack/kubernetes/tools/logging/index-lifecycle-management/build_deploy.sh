#!/usr/bin/env bash
set -euo pipefail

dir="$(dirname "$0")"
namespace="$1"

TAG="gcr.io/olly-2021-k8s-migration/index-lifecycle-management:latest"

docker build "$dir" -t "$TAG"
docker push "$TAG"

kubectl apply -n "$namespace" -f "$dir/job.yaml"