# Levels of the stack

We have set up multiple docker compose yaml to represent different levels of Observability within the same stack.

## Level 0
* Logs: unstructured, not aggregated or centralized.
* Monitoring: none
* Tracing: none
* *Challenge: In what order did a multiple transformation occur*

## Level 1
* Logs: unstructured, aggregated & centralized in EFK
* Monitoring: none
* Tracing: none
* *Challenge:*

## Level 2
* Logs: structured, aggregated & centralized in EFK
* Monitoring: none
* Tracing: none
* *Challenge:*

## Level 3
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: none
* Tracing: none
* *Challenge:*

## Level 4
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime (blackbox) monitoring in EFK
* Tracing: none
* *Challenge:*

## Level 5
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime and technical monitoring in prometheus / grafana
* Tracing: none
* *Challenge:*

## Level 6
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: none
* *Challenge:*

## Level 7
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: basic service to service tracing in zipkin
* *Challenge:*

## Level 8
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: business context event data included in service to service tracing in zipkin
* *Challenge:*

## Level 9
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: business context event data included in service to service tracing in zipkin and APM (tracing) in EFK
* *Challenge:*
