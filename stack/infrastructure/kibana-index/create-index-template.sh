#!/bin/bash

curl -X PUT "http://elastic:changeme@elasticsearch:9200/_template/logs?pretty" -H 'Content-Type: application/json' -d'
{
  "index_patterns": ["logs-*"],
  "settings": {
    "number_of_shards": 1
  },
  "mappings": {
    "properties": {
      "degrees":    { "type": "long" },
      "factor":    { "type": "long" },
      "horizontal":    { "type": "boolean" },
      "vertical":    { "type": "boolean" },
    }
  }
}
'
curl -X PUT "http://elastic:changeme@elasticsearch:9200/_template/logs?pretty" -H 'Content-Type: application/json' -d'
{
  "index_patterns": ["events-*"],
  "settings": {
    "number_of_shards": 1
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
      "event.transformation_rotate":    { "type": "boolean" }
    }
  }
}
'