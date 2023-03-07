#!/bin/bash

/usr/local/bin/docker-compose --project-directory /home/olly/observability-workshop/stack/compose/ \
        -f /home/olly/observability-workshop/stack/compose/docker-compose-level-9.yml \
        up --build -d 2>&1
