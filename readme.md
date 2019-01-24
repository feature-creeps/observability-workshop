# Observability & Testing Workshop

### Examples

Under stack we find an application folder which contains several entities for our observability stack:

| dir               | desc                      |
| ---               | ---                       |
| fluentbit/        | log collector             |
| fluentd/          | log collector/aggregator  |
| grafana/          | time series visualizer    |
| imageholder/      | sample application        |
| kibana/           | logs visualizer           |
| loadbalancer/     | nginx loadbalancer        |
| logstash/         | alternative log collector |
| prometheus/       | time series data base     |

In each stack-* directory is one or several *-compose.yml's:

| dir                   | desc                                                                                                                      |
| ---                   | ---                                                                                                                       |
| stack-local-default/  | this is what we start on a single node machine which is running independently. consists of the basic stack incl app       |
| stack-local-max/      | full stack including two load balanced sample apps using different log collectors                                         |
| stack-distributed/    | this is what we start on each client node and one ops node. additional configuration for service discovery is required!   |

### log integration
We use fluentd as the log collector, elastic search as the search engine and kibana as the visualizer UI.

Docker natively supports the fluentd log driver, which can be activated machine wide in the docker daemon of the machine or container specifically as seen in the docker-compose.yml of the imageholder integration tests.
All containers which provide logs need to connect to the fluentd service. The fluentd service is running in a seperate container. 
Since the daemon is responsible for the log driver initialization we cannot use the docker network dns name resolution. That's why we link the fluentd container and use localhost to resolve the service.

The fluentd service is configured with the in_forward plugin to listen to a TCP socket and receive the event stream.
Matching all tags it stores them into our elastic search instance.

Kibana displays logs from elasticsearch. The first time we start kibana, we need to create an index, which in our case is "fluentd-*". In the next step we use @timestamp as our basis for time filtering.
Under discover we can use queries to search for logs.

### monitoring integration
We use prometheus as the metrics collector and grafana for visualization.

grafana credentials: admin/admin