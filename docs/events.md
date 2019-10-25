#event


### definition
* scoped by the technical entity of a service
  * it 
* scoped by a technical transaction 
* scoped by one unit of work










#day 1

* exploring the business domain
* intro to olly
* events

* all gone & intro                  (30min)
  * close port to kibana
  * close port to grafana/prometheus
  * close port to zipkin
  * deactivate event emitting via config
  * stop metricbeat container
* pub keys

#day 2

* unstructured logs on the machine  (30min) [opt]
  * remove fluentd logging driver from compose
  * remove logback.xml via spring profile
* unstructured logs on kibana       (60min)
  * open kibana port
  * reactivate fluentd logging driver in compose
* structured logs on kibana         (45min)
  * reactivate default spring profile (remove env var config)
* events on kibana                  (10min)
  * reactivate event emitting via config
* metrics on prometheus/grafana     (90min)
  * open port to prometheus/grafana
* metrics in kibana                 (?)
  * start metricbeat
* alerts based on metrics           (20min) [opt]
  * just theory
* tracing in zipkin                 (60min)
  * 
* tracing in kibana                 (10min)
* debrief & feedback                (60min)