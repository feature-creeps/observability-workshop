#!/usr/bin/env bash
set -euo pipefail

script_dir="$(dirname "$0")"

image_prefix="local/dima-"
image_tag="$(git rev-parse --short HEAD)-snapshot"

services=(
  "imageorchestrator"
  "imageholder"
  "imagerotator"
  "imagegrayscale"
  "imageresize"
  "imageflip"
  "imagethumbnail"
  "trafficgen"
)

for service in "${services[@]}"; do
  docker build "${script_dir}/../../stack/application/" --build-arg "SERVICE=$service"  -t "${image_prefix}${service}:${image_tag}"
done

docker build "${script_dir}/../../stack/application/frontend/" -t "${image_prefix}frontend:${image_tag}"
