FROM appropriate/curl

ENTRYPOINT curl "http://kibana:5601/api/saved_objects/index-pattern" | grep "fluentd-" \
           || curl -f -XPOST -H "Content-Type: application/json" \
              -H "kbn-xsrf: anything" \
              "http://kibana:5601/api/saved_objects/index-pattern" \
              -d"{\"attributes\":{\"title\":\"fluentd-*\",\"timeFieldName\":\"@timestamp\"}}"