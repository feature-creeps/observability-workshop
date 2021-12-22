#!/usr/bin/env bash
set -euo pipefail

SA_NAME=${1:-"github-actions"}
SA_EMAIL="${SA_NAME}@olly-2021-k8s-migration.iam.gserviceaccount.com"
GKE_PROJECT="olly-2021-k8s-migration"

gcloud iam service-accounts create "$SA_NAME"

gcloud projects add-iam-policy-binding "$GKE_PROJECT" \
  --member=serviceAccount:"$SA_EMAIL" \
  --role=roles/container.admin \
  --role=roles/storage.admin

gcloud iam service-accounts keys create key.json --iam-account="$SA_EMAIL"

# todo: how to download the key.json later for creating an image pull secret