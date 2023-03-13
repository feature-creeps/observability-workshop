#!/usr/bin/env bash
set -euo pipefail

export GKE_REGISTRY=local
export GITHUB_SHA="$(git rev-parse --short HEAD)-snapshot"

cd ../stack/compose
docker compose -f docker-compose.yml -f docker-compose-registry-tags.yml build --parallel