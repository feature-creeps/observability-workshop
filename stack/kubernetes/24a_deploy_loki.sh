#!/usr/bin/env bash
set -euo pipefail

ZONE="europe-west1"
NAMESPACE=logging
PROJECT="olly-2021-k8s-migration"
SA_NAME=loki-sa
SA_MAIL="${SA_NAME}@${PROJECT}.iam.gserviceaccount.com"
KEY_FILE=/tmp/loki-gcp-key.json
SECRET=loki-secrets
BUCKET_NAME="loki_bucket_o11y_fans"

echo "=== Setup GCS as cloud storage and deploy loki stack in namespace ${NAMESPACE}"

echo "--- setup GCS as cloud storage for Loki"
gsutil ls "gs://$BUCKET_NAME/" || gsutil mb -b on -l "$ZONE" -p "$PROJECT" "gs://$BUCKET_NAME/"

echo "--- create service account for Loki to access GCS"
gcloud iam service-accounts describe "$SA_MAIL" > /dev/null || gcloud iam service-accounts create "$SA_NAME" --project "$PROJECT" --display-name="Service account for Loki"

gcloud projects add-iam-policy-binding "$PROJECT" \
--member="serviceAccount:$SA_MAIL" \
--project "$PROJECT" \
--role="roles/storage.objectAdmin"

echo "--- create service account secret"
gcloud iam service-accounts keys create "$KEY_FILE" --iam-account="$SA_MAIL"
kubectl delete secret "$SECRET" -n "$NAMESPACE" || true
kubectl create secret generic "$SECRET" --from-file=gcp_service_account.json="$KEY_FILE" -n "$NAMESPACE"
rm $KEY_FILE

echo "--- install and update helm repos"
helm repo update
helm repo add loki-helm https://grafana.github.io/helm-charts/

echo "--- install loki"
helm upgrade --install -n "$NAMESPACE" -f tools/logging/loki.yaml --create-namespace loki grafana/loki
