#!/bin/bash

RETENTION_IN_DAYS=$1
ELASTIC_HOST=elasticsearch-master

EXIT=0

echo "applying index lifecycle management with retention period of $RETENTION_IN_DAYS days to"
curl -f -XPOST -H "Content-Type: application/json" \
    "http://$ELASTIC_HOST:9200/_ilm/policy/index-retention" \
    -d"
{
  'policy': {
    'phases': {
      'delete': {
        'min_age': '$RETENTION_IN_DAYS',
        'actions': {
          'delete': {}
        }
      }
    }
  }
}
"