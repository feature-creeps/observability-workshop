#!/bin/bash

INDICES=$1
KIBANA_HOST=kibana

echo "creating the following index patterns: $INDICES*"
for INDEX in $INDICES; do
    curl "http://$KIBANA_HOST:5601/api/saved_objects/index-pattern/$INDEX" | grep "$INDEX" \
               || curl -f -XPOST -H "Content-Type: application/json" \
                  -H "kbn-xsrf: anything" \
                  "http://$KIBANA_HOST:5601/api/saved_objects/index-pattern/$INDEX" \
                  -d"{\"attributes\":{\"title\":\"$INDEX*\",\"timeFieldName\":\"@timestamp\"}}"
done