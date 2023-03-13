#!/usr/bin/env bash
set -euo pipefail

ZONE="europe-west1"
NAMESPACE=monitoring
PROJECT="olly-2021-k8s-migration"
SA_NAME=tempo-sa
SA_MAIL="${SA_NAME}@${PROJECT}.iam.gserviceaccount.com"
KEY_FILE=/tmp/tempo-gcp-key.json
SECRET=tempo-secrets
BUCKET_NAME="tempo_bucket_o11y_fans"

echo "=== Setup GCS as cloud storage and deploy tempo stack in namespace ${NAMESPACE}"
echo "!!! this should not be run for the local deployment as it will mess with the remote logging !!!"

echo "--- setup GCS as cloud storage for Tempo"
if ! gsutil ls "gs://$BUCKET_NAME/" > /dev/null; then
    echo Bucket "$BUCKET_NAME" does not exist yet...
    gsutil mb -b on -l "$ZONE" -p "$PROJECT" "gs://$BUCKET_NAME/"
else
    echo Bucket "$BUCKET_NAME" already exists, skipping creation...
fi

echo "--- create service account for Tempo to access GCS"
if ! gcloud iam service-accounts describe "$SA_MAIL" > /dev/null; then
    echo Service account "$SA_MAIL" does not exist yet...
    gcloud iam service-accounts create "$SA_NAME" --project "$PROJECT" --display-name="Service account for Tempo"
else
    echo Service account "$SA_MAIL" already exists, skipping creation...
fi

echo "--- assigning storage.admin role to service account"
gcloud projects add-iam-policy-binding "$PROJECT" \
--member="serviceAccount:$SA_MAIL" \
--project "$PROJECT" \
--role="roles/storage.admin"

echo "--- create service account secret"
gcloud iam service-accounts keys create "$KEY_FILE" --iam-account="$SA_MAIL"
kubectl delete secret "$SECRET" -n "$NAMESPACE" --ignore-not-found=true
kubectl create secret generic "$SECRET" --from-file=gcp_service_account.json="$KEY_FILE" -n "$NAMESPACE"
rm $KEY_FILE

# # echo "--- install grafana tempo"
helm upgrade --install -n "$NAMESPACE" -f tools/tempo/tempo.yaml --create-namespace tempo grafana/tempo-distributed