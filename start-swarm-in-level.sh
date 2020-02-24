#!/bin/bash
lvl=$1
cd $HOME/observability-workshop/stack/compose/
docker-compose -f docker-swarm-level-$lvl.yml build
docker-compose -f docker-swarm-level-$lvl.yml pull
docker-compose -f docker-swarm-level-$lvl.yml push
docker stack deploy --compose-file docker-swarm-level-$lvl.yml --prune o11ystack