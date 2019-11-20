#!/bin/bash
lvl=$1
cd stack/compose/
docker-compose -f docker-compose-level-$lvl.yml up -d --build --remove-orphans