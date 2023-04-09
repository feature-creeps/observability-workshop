#!/usr/bin/env bash
set -euo pipefail

CLUSTER_NAME=o11y-stack

echo "Setting up k3d cluster '$CLUSTER_NAME'"

if k3d cluster list | grep $CLUSTER_NAME > /dev/null; then
    echo "Cluster already exists, skipping ..."
else
    k3d cluster create $CLUSTER_NAME \
        -p "8080:80@loadbalancer" \
        --k3s-arg "--disable=traefik@server:0" \
        --agents 0
fi

kubectl apply -f infrastructure/namespaces.yaml
