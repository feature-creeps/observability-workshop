# local

This folder contains scripts and configurations for local build and deployment.

The build use the docker compose build in [../stack/compose](../stack/compose) as in the pipeline.

The deployment scripts and configuration mimick the remote ones from [../stack/kubernetes](../stack/kubernetes) and uses the helm templates therein.

## Preqrequisites

To startup the local setup, you need the tool [kind](https://kind.sigs.k8s.io/), which deploys a lightweight kubernetes cluster in your local setup using docker containers. Also you will need the [Flux CLI](https://fluxcd.io/flux/cmd/) installed on your machine.

## Setup

First, startup your kind cluster using the script [00_setup_kind.sh](./kubernetes/00_setup_kind.sh). Flux will automatically deploy the infrastructure.

Once the setup is complete, you can deploy the application services using the scripts [10_build_dima.sh](./kubernetes/10_build_dima.sh) and [11_deploy_dima.sh](./kubernetes/11_deploy_dima.sh).

To access the system, you need to port-forward the ingress controller service to your host machine using the command `kubectl port-forward -n ingress svc/nginx-ingress-ingress-nginx-controller 8080:80`.