#!/bin/bash
lvl=$1
cd /home/olly/observability-workshop/stack/compose/
docker-compose -f docker-compose-level-$lvl.yml up -d --remove-orphans --build
