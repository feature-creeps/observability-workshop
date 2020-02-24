#!/bin/bash
lvl=$1
cd $HOME/observability-workshop/stack/compose/
START=`date +%s`
docker-compose -f docker-compose-level-$lvl.yml --compatibility up -d --build --remove-orphans

END=`date +%s`
curl https://api.honeycomb.io/1/markers/$HONEYCOMB_DATASET -X POST  \
    -H "X-Honeycomb-Team: $HONEYCOMB_KEY"  \
    -d "{\"message\":\"start-stack $lvl\", \"type\":\"deploy\", \"start_time\": $START, \"end_time\": $END}"