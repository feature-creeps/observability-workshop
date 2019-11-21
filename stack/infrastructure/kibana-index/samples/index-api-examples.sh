# look up mapping of index

curl -XGET 'http://elastic:changeme@localhost:9200/reindexed-logs-20191102/_mapping'


# look up index pattern

curl -XGET 'http://http://localhost:5601/api/saved_objects/index-pattern/events-*'


# create indices with mapping

for date in 1027 1028 1029 1030 1031 1101 1102; do
curl -XPUT http://elastic:changeme@localhost:9200/reindexed-logs-2019$date -H 'Content-Type:application/json' -d '
{
  "mappings": {
    "properties": {
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
done



# reindex

curl -X POST http://elastic:changeme@localhost:9200/_reindex?pretty -H 'Content-Type: application/json' -d'
{
  "source": {
    "index": "logs-20191101"
  },
  "dest": {
    "index": "reindexed-logs-20191027"
  },
  "conflicts" : "proceed"
}
'


# delete mapping

curl -XDELETE http://elastic:changeme@localhost:9200/logs-20191102/_mapping