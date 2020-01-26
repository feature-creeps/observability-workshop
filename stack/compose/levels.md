# Levels of the stack

We have set up multiple docker compose yaml to represent different [levels of Observability within the same stack. Starting at level 0, there is a challenge which should be attemped at both the given level and the next to compare your experiences given the different available tooling.

## [Level 0](./docker-compose-[level-0.yml)
* Logs: unstructured, not aggregated or centralized.
* Monitoring: none
* Tracing: none
* *Challenge: Which transformation has been applied to the PNG image types the most?*

## [Level 1](./docker-compose-level-1.yml)
* Logs: unstructured, aggregated & centralized in EFK
* Monitoring: none
* Tracing: none
* *Challenge: What is the highest factor an image has been resized by?*

## [Level 2](./docker-compose-level-2.yml)
* Logs: structured, aggregated & centralized in EFK
* Monitoring: none
* Tracing: none
* *Challenge: What percentage of rotations failed in the last 5 minutes?*

## [Level 3](./docker-compose-level-3.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: none
* Tracing: none
* *Challenge: *

## [Level 4](./docker-compose-level-4.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime (blackbox) monitoring in EFK
* Tracing: none
* *Challenge:*

## [Level 5](./docker-compose-level-5.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime and technical monitoring in prometheus / grafana
* Tracing: none
* *Challenge:*

## [Level 6](./docker-compose-level-6.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: none
* *Challenge:*

## [Level 7](./docker-compose-level-7.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: basic service to service tracing in zipkin
* *Challenge:*

## [Level 8](./docker-compose-level-8.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: business context event data included in service to service tracing in zipkin
* *Challenge:*

## [Level 9](./docker-compose-level-9.yml)
* Logs: structured events and logs, aggregated & centralized in EFK
* Monitoring: uptime, technical & business monitoring in prometheus / grafana
* labeled logs in loki / grafana
* Tracing: business context event data included in service to service tracing in zipkin and APM (tracing) in EFK
* *Challenge:*
