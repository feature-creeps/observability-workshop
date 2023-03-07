#!/bin/bash

SERVICES="imageholder imageorchestrator imagethumbnail imageresize imagerotator imagegrayscale imageflip frontend"

echo "start: resetting dima BUSINESS stack at $(date)"
echo "BUSINESS stack affects the following services: $SERVICES"

export HONEYCOMB_KEY=e621674c7881d52b3e7ee647744a3fbd
export HONEYCOMB_ENABLED=true

git -C /home/olly/observability-workshop pull 

/usr/local/bin/docker-compose --project-directory /home/olly/observability-workshop/stack/compose/ \
	-f /home/olly/observability-workshop/stack/compose/docker-compose-level-9.yml \
	up --build -d $SERVICES 2>&1

echo "finish: resetting dima BUSINESS stack at $(date)"


