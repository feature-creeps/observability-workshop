# Levels of the stack


We have set up multiple docker compose yamls to represent different levels of Observability within the same stack.

##### Level 0
* unstructured, one liner logs, not aggregated & centralized
* no monitoring
* no tracing

##### Level 1
* unstructured, one liner logs, aggregated & centralized in EFK
* no monitoring
* no tracing

##### Level 2
* structured, one liner logs, aggregated & centralized in EFK
* no monitoring
* no tracing

##### Level 3
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* no monitoring
* no tracing

##### Level 4
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* only uptime (blackbox) monitoring
* no tracing

##### Level 5
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* only technical monitoring in prometheus / grafana
* no tracing

##### Level 6
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* no tracing

##### Level 7
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* non centextual tracing in zipkin

##### Level 8
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* centextual tracing in zipkin

##### Level 8
* events in EFK
* structured, one liner logs, aggregated & centralized in EFK
* technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* centextual tracing in zipkin
* APM (tracing) in EFK