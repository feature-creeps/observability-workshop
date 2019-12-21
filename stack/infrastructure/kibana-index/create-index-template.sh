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
      "span.duration_ms":    { "type": "integer" },
      "span.content_size":    { "type": "integer" },
      "span.content_size_new":    { "type": "integer" },
      "span.content_size_original":    { "type": "integer" },
      "span.finishedAt":    { "type": "date" },
      "span.response_status":    { "type": "integer" },
      "span.startedAt":    { "type": "date" },
      "span.transformation_flip":    { "type": "boolean" },
      "span.transformation_greyscale":    { "type": "boolean" },
      "span.transformation_image_persist":    { "type": "boolean" },
      "span.transformation_resize":    { "type": "boolean" },
      "span.transformation_rotate":    { "type": "boolean" }
    }
  }
}
'