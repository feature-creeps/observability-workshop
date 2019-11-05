#!/bin/bash
lvl=$1
cd stack/stack-full/
docker-compose -f docker-compose-level-$lvl.yml up -d --build --remove-orphans