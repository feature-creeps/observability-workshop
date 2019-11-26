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
      "span.tranformation_flip":    { "type": "boolean" },
      "span.tranformation_greyscale":    { "type": "boolean" },
      "span.tranformation_image_persist":    { "type": "boolean" },
      "span.tranformation_resize":    { "type": "boolean" },
      "span.tranformation_rotate":    { "type": "boolean" }
    }
  }
}
'