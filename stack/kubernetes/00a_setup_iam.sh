#!/usr/bin/env bash
set -euo pipefail

# iam for github actions, see https://github.com/google-github-actions/auth

GKE_PROJECT="olly-2021-k8s-migration"
WIP="github-pool"
WIP_PROVIDER="github-provider"
SA_NAME=${1:-"github-actions"}
SA_EMAIL="${SA_NAME}@${GKE_PROJECT}.iam.gserviceaccount.com"

gcloud iam workload-identity-pools create "${WIP}" \
  --project="${GKE_PROJECT}" \
  --location="global" \
  --display-name="Github pool"

gcloud iam workload-identity-pools providers create-oidc "${WIP_PROVIDER}" \
  --project="${GKE_PROJECT}" \
  --location="global" \
  --workload-identity-pool="${WIP}" \
  --display-name="Github provider" \
  --attribute-mapping="google.subject=assertion.sub,attribute.actor=assertion.actor,attribute.aud=assertion.aud" \
  --attribute-mapping="google.subject=assertion.sub,attribute.repository=assertion.repository" \
  --issuer-uri="https://token.actions.githubusercontent.com"

gcloud iam service-accounts create "$SA_NAME"

gcloud projects add-iam-policy-binding "$GKE_PROJECT" \
  --member=serviceAccount:"$SA_EMAIL" \
  --role=roles/container.admin \
  --role=roles/storage.admin

gcloud iam service-accounts add-iam-policy-binding "${SA_EMAIL}" \
  --project="${GKE_PROJECT}" \
  --role=roles/container.admin \
  --role=roles/storage.admin \
  --role="roles/iam.workloadIdentityUser" \
  --member="principalSet://iam.googleapis.com/projects/399309401465/locations/global/workloadIdentityPools/${WIP}/attribute.repository/feature-creeps/observability-workshop"

gcloud iam service-accounts keys create key.json --iam-account="$SA_EMAIL"

# todo: how to download the key.json later for creating an image pull secret