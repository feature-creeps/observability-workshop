#!/bin/bash

cd /home/olly/observability-workshop/stack/compose

docker-compose -f docker-compose-level-0.yml down --remove-orphans
