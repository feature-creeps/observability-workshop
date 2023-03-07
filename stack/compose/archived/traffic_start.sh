#!/bin/bash

cd /home/olly/observability-workshop/stack/compose

docker-compose -f docker-compose-level-9.yml up -d traffic-gen
