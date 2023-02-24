#!/usr/bin/env bash
set -eu -o pipefail

# minimal profile does not deploy egress/ingress, see https://istio.io/latest/docs/setup/additional-setup/config-profiles/
istioctl install --set profile=minimal -y

kubectl create clusterrolebinding cluster-admin-binding --clusterrole cluster-admin --user "$(gcloud config get-value account)" || true
