#!/bin/bash
lvl=$1
cd $HOME/observability-workshop/stack/compose/

docker-compose -f docker-swarm-level-$lvl.yml pull
docker-compose -f docker-swarm-level-$lvl.yml build
docker-compose -f docker-swarm-level-$lvl.yml push

START=`date +%s`
docker stack deploy --compose-file docker-swarm-level-$lvl.yml --prune o11ystack

END=`date +%s`
curl https://api.honeycomb.io/1/markers/$HONEYCOMB_DATASET -X POST  \
    -H "X-Honeycomb-Team: $HONEYCOMB_KEY"  \
    -d "{\"message\":\"start-stack $lvl\", \"type\":\"deploy\", \"start_time\": $START, \"end_time\": $END}"