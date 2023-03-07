#!/bin/bash

echo "start: resetting dima stack at $(date)"

export HONEYCOMB_KEY=e621674c7881d52b3e7ee647744a3fbd
export HONEYCOMB_ENABLED=true

/usr/local/bin/docker-compose --project-directory /home/olly/observability-workshop/stack/compose/ \
	-f /home/olly/observability-workshop/stack/compose/docker-compose-level-9.yml \
	down -v 2>&1

git -C /home/olly/observability-workshop pull 

/usr/local/bin/docker-compose --project-directory /home/olly/observability-workshop/stack/compose/ \
	-f /home/olly/observability-workshop/stack/compose/docker-compose-level-9.yml \
	up --build -d 2>&1

echo "finish: resetting dima stack at $(date)"


