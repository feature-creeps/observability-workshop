FROM appropriate/curl

COPY default-mappings.json .

CMD curl --connect-timeout 5 \
             --max-time 10 \
             --retry 5 \
             --retry-delay 0 \
             --retry-max-time 40 \
             -X PUT 'elasticsearch:9200/fluentd' -H 'Content-Type: application/json' -d@default-mappings.json