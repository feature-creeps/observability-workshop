# Observability & Testing Workshop based on Dima WebApp

## Disclaimer

**This application has little security built in, so it can be at risk if left running and open to the internet. You are
running this at your own risk.**

## License

This application runs under the [Apache 2.0 License](LICENSE) which you can read more
about [here](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)) as well.

## Welcome to Dima

The Dima application is a web application with
basic [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) functionality around images. The main goal
of this repository is to provide a playground for software professionals to practice their debugging and instrumenting
of software applications for greater observability. Therefore, the minimal Dima WebApp is meant as a way to exercise an
overpowered telemetry stack.

There will always be bugs and issues that can be found. Some are planned, and others are fun coincidences. If you find
something not in the [known bugs file](docs/known_bugs.md) please create a pull request and we would love to know about
it.

## Running the stack

### Preparing infrastructure

This stack takes a fair bit of RAM to run successfully, therefore we suggest running on a cloud managed kubernetes
cluster. For GKE a setup incl. scripts can be found [here](stack/kubernetes).

### Selecting the application to run

The application images are built via the docker-compose file found under `stack/compose`.

### What you are building

Our application is made up of a WebApp to upload, manipulate, view, and delete images as well as extensive telemetry
tooling. For a visual representation of our application, check out
the [architecture diagram](docs/architecture/architecture.pdf) or
the [infrastructure diagram](docs/architecture/architecture.pages).
> NOTE: These images are prone to getting out of date. Pull requests welcome!

#### Key application credentials

Grafana credentials: grafana/changeme

Kibana credentials: elastic/changeme

#### Application services

| dir                | desc                                           |
| ---                | ---                                            |
| frontend/          | app for displaying and interacting             |
| imageorchestrator/ | completes all requested manipulations          |
| imageholder/       | image upload and viewing                       |
| imagerotator/      | image rotation                                 |
| imagegrayscale/    | changes image to grayscale                     |
| imageresize/       | resizes up or down by multiples                |
| imageflip/         | horizontal and vertical flip options           |
| imagethumbnail/    | minimises images for quick display / preview   |

#### Observability tools

| dir                        | desc                                      |
| ---                        | ---                                       |
| apm-server                 | apm / tracing for EFK                     |
| curator                    | elasticsearch cluster management          |
| fluentd                    | log collector/aggregator                  |
| grafana                    | time series visualizer                    |
| heartbeat                  | uptime monitoring                         |
| index-lifecycle-managament | log index lifecycle management            |
| kiali                      | service mesh UI                           |
| kibana                     | time series visualizer                    |
| kibana-index               | logs visualizer                           |
| loadbalancer               | nginx loadbalancer                        |
| logstash                   | alternative log collector                 |
| prometheus                 | time series data base                     |
| traffic-gen                | configurable image uploader / manipulator |
| zipkin                     | tracing (in the docker-compose files)     |

### Technical decisions

#### Log integration

We use fluentd as the log collector, elastic search as the search engine and kibana as the visualizer UI.

All containers write their logs to stdout, ideally formatted as json. The fluentd service is running as a daemonset and
collecting, parsing and sending logs from all nodes to elasticsearch.

Kibana displays logs from elasticsearch. We create index patterns "logs-*" and "events-*".

#### Monitoring integration

We use prometheus as the metrics collector and grafana for visualization.

#### Tracing integration

We use jaeger and elastic apm for tracing.