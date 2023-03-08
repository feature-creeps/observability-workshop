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
cluster. For GKE a setup incl. scripts can be found in [stack/kubernetes](stack/kubernetes).

### Selecting the application to run

The application images are built via the docker-compose file found under [stack/compose](stack/compose).

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

The following application services can be found under [stack/application](stack/application).


| Service                                                  | Description                                    |
| ---                                                      | ---                                            |
| [frontend](stack/application/frontend)                   | app for displaying and interacting             |
| [imageorchestrator](stack/application/imageorchestrator) | completes all requested manipulations          |
| [imageholder](stack/application/imageholder)             | image upload and viewing                       |
| [imagerotator](stack/application/imagerotator)           | image rotation                                 |
| [imagegrayscale](stack/application/imagegrayscale)       | changes image to grayscale                     |
| [imageresize](stack/application/imageresize)             | resizes up or down by multiples                |
| [imageflip](stack/application/imageflip)                 | horizontal and vertical flip options           |
| [imagethumbnail](stack/application/imagethumbnail)       | minimises images for quick display / preview   |
| [trafficgen](stack/application/trafficgen)               | configurable image uploader / manipulator      |

#### Observability tools

The following observability tools are used in this project.

| Tool                                                                                      | Description                               |
| ---                                                                                       | ---                                       |
| [Elastic APM](https://www.elastic.co/de/observability/application-performance-monitoring) | apm / tracing for EFK                     |
| [Elastic Curator](https://github.com/elastic/curator)                                     | elasticsearch cluster management          |
| [Elastic Heartbeat](https://www.elastic.co/de/beats/heartbeat)                            | uptime monitoring                         |
| [Elastic Kibana](https://www.elastic.co/de/kibana/)                                       | time series/logs visualizer               |
| [fluentd](https://www.fluentd.org/)                                                       | log collector/aggregator                  |
| [Grafana](https://grafana.com/)                                                           | time series visualizer                    |
| [Google Cloud Logging](https://cloud.google.com/logging?hl=de)                            | Preview: Alternative zu EFK               |
| index-lifecycle-managament                                                                | log index lifecycle management            |
| [Jaeger](https://www.jaegertracing.io/)                                                   | tracing (in the docker-compose files)     |
| [Kiali](https://kiali.io/)                                                                | service mesh UI                           |
| [Prometheus](https://prometheus.io/)                                                      | time series data base                     |

### Technical decisions

#### Log integration

We use fluentd as the log collector, Elasticsearch as the search engine and Kibana as the visualizer UI.

All containers write their logs to stdout, ideally formatted as json. The fluentd service is running as a daemonset and
collecting, parsing and sending logs from all nodes to Elasticsearch.

Kibana displays logs from elasticsearch. We create index patterns "logs-*" and "events-*".

#### Monitoring integration

We use Prometheus as the metrics collector and Grafana for visualization.

#### Tracing integration

We use Jaeger and Elastic APM for tracing.