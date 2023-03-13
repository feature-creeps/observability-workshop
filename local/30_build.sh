#!/usr/bin/env bash
set -euo pipefail

export GKE_REGISTRY=local
export GITHUB_SHA="$(git rev-parse --short HEAD)-snapshot"

docker compose -f ../stack/compose/docker-compose.yml -f ../stack/compose/docker-compose-registry-tags.yml build --parallel