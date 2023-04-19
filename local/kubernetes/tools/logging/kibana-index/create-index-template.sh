#!/usr/bin/env sh

ELASTICSEARCH_HOST=elasticsearch-master

echo "write index template for logs-*"
curl -X PUT "http://$ELASTICSEARCH_HOST:9200/_index_template/log?pretty" -H 'Content-Type: application/json' -d'
{
  "index_patterns": ["logs-*"],
  "priority" : 1,
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 1,
      "index.lifecycle.name": "logs_policy"
    },
    "mappings": {
      "properties": {
        "degrees":    { "type": "long" },
        "factor":    { "type": "long" },
        "horizontal":    { "type": "boolean" },
        "vertical":    { "type": "boolean" }
      }
    }
  }
}
'

echo "write index template for events-*"
curl -X PUT "http://$ELASTICSEARCH_HOST:9200/_index_template/event?pretty" -H 'Content-Type: application/json' -d'
{
  "index_patterns": ["events-*"],
  "priority" : 1,
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 1,
      "index.lifecycle.name": "logs_policy"
    },
    "mappings": {
      "properties": {
        "degrees":    { "type": "long" },
        "factor":    { "type": "long" },
        "horizontal":    { "type": "boolean" },
        "vertical":    { "type": "boolean" },
        "event.duration_ms":    { "type": "integer" },
        "event.content_size":    { "type": "integer" },
        "event.content_size_new":    { "type": "integer" },
        "event.content_size_original":    { "type": "integer" },
        "event.finishedAt":    { "type": "date" },
        "event.response_status":    { "type": "integer" },
        "event.startedAt":    { "type": "date" },
        "event.transformation_flip":    { "type": "boolean" },
        "event.transformation_greyscale":    { "type": "boolean" },
        "event.transformation_image_persist":    { "type": "boolean" },
        "event.transformation_resize":    { "type": "boolean" },
        "event.transformation_rotate":    { "type": "boolean" },
        "event.request_header_x-real-ip":    { "type": "text" },
        "event.request_header_cookie":    { "type": "text" },
        "event.user":    { "type": "text" }
      }
    }
  }
}
'
echo ""