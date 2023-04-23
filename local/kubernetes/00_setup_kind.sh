#!/usr/bin/env bash
set -euo pipefail

echo "=== Setting up kubernetes cluster"
echo "--- create cluster using kind"

if kind get clusters | grep kind > /dev/null; then
  echo "Cluster already exists, skipping ..."
else
  kind create cluster
fi

echo "--- create namespaces"
kubectl apply -f namespaces.yaml

echo "--- install flux"
flux install

echo "--- deploy flux configurations"
kubectl apply -f ../../clusters/dev/flux-system
