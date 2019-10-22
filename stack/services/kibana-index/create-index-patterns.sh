#!/bin/bash

INDICES=$1
KIBANA_HOST=kibana

EXIT=0

echo "creating the following index patterns: $INDICES*"
for INDEX in $INDICES; do
    curl --fail "http://$KIBANA_HOST:5601/api/saved_objects/index-pattern/$INDEX"
    missing=$?
    if [[ "$missing" -ne "0" ]]; then
       curl -f -XPOST -H "Content-Type: application/json" \
          -H "kbn-xsrf: anything" \
          "http://$KIBANA_HOST:5601/api/saved_objects/index-pattern/$INDEX" \
          -d"{\"attributes\":{\"title\":\"$INDEX*\",\"timeFieldName\":\"@timestamp\"}}"
       let EXIT=$EXIT + $?
    fi
done

exit $EXIT