# Observability & Testing Workshop

## Disclaimer

This application has little to no security built in so can be at risk if left running and open to the internet and you are running this at your own risk.

This is a playground for software professions to practice their debugging and instrumenting of software applications for greater observability. This point, there will always be bugs and issues that can be found. Some are planned, and others are fun coincidences. If you find something not in the [known bugs file](known_bugs.md) please creat a pull request and we would love to know about it.

This application runs under the [Apache 2.0 License](LICENSE) which you can read more about [here](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)) as well.

## Running the stack

### On AWS

[Instructions](run_stack_on_aws.md)

## Architecture

[PDF diagram](architecture.pdf) or in [Pages](architecture.pages)
> NOTE: This may need updating

### Application

Under stack we find an application folder which contains several entities for our observability stack:

| dir                | desc                                                  |
| ---                | ---                                                   |
| fluentbit/         | log collector                                         |
| fluentd/           | log collector/aggregator                              |
| frontend/          | app for displaying and interacting                    |
| grafana/           | time series visualizer                                |
| imageflip/         |                                                       |
| imagegrayscale/    |                                                       |
| imageholder/       | sample application for image upload and viewing       |
| imageorchestrator/ |                                                       |
| imageresize/       |                                                       |
| imagerotator/      | sample application for image rotation                 |
| imagethumbnail/    |                                                       |
| kibana/            | logs visualizer                                       |
| kibana-index/      |                                                       |
| loadbalancer/      | nginx loadbalancer                                    |
| logstash/          | alternative log collector                             |
| prometheus/        | time series data base                                 |
| traffic-gen/       |                                                       |

The `stack-full` directory is one or several `*-compose.yml`'s and is where we start all services on a single node machine which is running independently. consists of the basic stack incl app

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