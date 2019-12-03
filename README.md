# Observability & Testing Workshop based on Dima WebApp

## Disclaimer

**This application has little to no security built in so can be at risk if left running and open to the internet and you are running this at your own risk.**

## License

This application runs under the [Apache 2.0 License](LICENSE) which you can read more about [here](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)) as well.

## Welcome to Dima

The Dima application is a web application with basic [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) functionality around images. The main goal of this repository is to provide a playground for software professionals to practice their debugging and instrumenting of software applications for greater observability. Therefore, the minimal Dima WebApp is meant as a way to exercise an overpowered telemetry stack.

There will always be bugs and issues that can be found. Some are planned, and others are fun coincidences. If you find something not in the [known bugs file](docs/known_bugs.md) please create a pull request and we would love to know about it.

## Running the stack

### Preparing infrastructure

On AWS: [Instructions](docs/run_stack_on_aws.md)

On ubuntu: [Instructions](docs/run_on_ubuntu.md)

### Selecting the application to run

The application and infrastructure are built via the docker-compose files found under `stack/compose`. The level files are created so that people can upgrade and downgrade without concern of order.

1. Select the level you would like to run as detailed in [levels.md](stack/compose/levels.md).
1. From the root directory use the `start-stack-in-level.sh` file by passing in a an integer 0 to 9. E.g.
`bash
start-stack-in-level.sh 5
`
    > Note: If you see an error like `ERROR: Couldn't connect to Docker daemon - you might need to run 'docker-machine start default'.` you may need to run in as sudo.

### What you are building

Our application is made up of a WebApp to upload, manipulate, view, and delete images as well as extensive telemetry tooling. For a visual representation of our application, please see the [PDF diagram](docs/architecture/architecture.pdf) or the [Pages](docs/architecture/architecture.pages) display.
> NOTE: These images are prone to getting out of date. Pull requests welcome!

#### Key application credentials

Grafana credentials: admin/admin

Kibana credentials: elastic/changeme

#### Application components (`stack/application`)

| dir                | desc                                           | Running port |
| ---                | ---                                            | ---          |
| frontend/          | app for displaying and interacting             | 80           |
| imageorchestrator/ | completes all requested manipulations          | 8080         |
| imageholder/       | image upload and viewing                       | 8081         |
| imagerotator/      | image rotation                                 | 8082         |
| imagegrayscale/    | changes image to grayscale                     | 8083         |
| imageresize/       | resizes up or down by multiples                | 8084         |
| imageflip/         | horizontal and vertical flip options           | 8085         |
| imagethumbnail/    | minimises images for quick display / preview   | 8086         |

#### Telemetry infrastructure components (`stack/infrastructure`)

| dir                         | desc                                      |Running port |
| ---                         | ---                                       | --          |
| apm-server/                 | apm / tracing for EFK                     |             |
| curator/                    | elasticsearch cluster management          |             |
| fluentbit/                  | log collector                             |             |
| fluentd/                    | log collector/aggregator                  |             |
| grafana/                    | time series visualizer                    | 3000        |
| heartbeat                   | uptime monitoring                         |             |
| index-lifecycle-managament/ | log index lifecycle management            |             |
| kibana/                     | time series visualizer                    | 5601        |
| kibana-index/               | logs visualizer                           |             |
| loadbalancer/               | nginx loadbalancer                        |             |
| logstash/                   | alternative log collector                 |             |
| prometheus/                 | time series data base                     | 9090        |
| traffic-gen/                | configurable image uploader / manipulator |             |
| zipkin                      | tracing (in the docker-compose files)     | 9411        |

### Technical decisions

#### Log integration

We use fluentd as the log collector, elastic search as the search engine and kibana as the visualizer UI.

Docker natively supports the fluentd log driver, which can be activated machine wide in the docker daemon of the machine or container specifically as seen in the docker-compose.yml of the imageholder integration tests.

All containers which provide logs need to connect to the fluentd service. The fluentd service is running in a seperate container. 

Since the daemon is responsible for the log driver initialization we cannot use the docker network dns name resolution. That's why we link the fluentd container and use localhost to resolve the service.

The fluentd service is configured with the in_forward plugin to listen to a TCP socket and receive the event stream.
Matching all tags it stores them into our elastic search instance.

Kibana displays logs from elasticsearch. The first time we start kibana, we need to create an index, which in our case is "fluentd-*". In the next step we use @timestamp as our basis for time filtering.
Under discover we can use queries to search for logs.

#### Monitoring integration

We use prometheus as the metrics collector and grafana for visualization.
