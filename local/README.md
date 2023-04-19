# local

This folder contains scripts and configurations for local build and deployment.

The build use the docker compose build in [../stack/compose](../stack/compose) as in the pipeline.

The deployment scripts and configuration mimick the remote ones from [../stack/kubernetes](../stack/kubernetes) and uses the helm templates therein.

## Preqrequisites

To startup the local setup, you need the tool [k3d](https://k3d.io/) by Rancher, which deploys a lightweight k3s cluster in your local setup using docker containers.

## Setup

First, startup your k3d cluster using the script [00_setup_k3d.sh](./kubernetes/00_setup_k3d.sh). Once the setup is complete, you can deploy the application services using the scripts [10_build_dima.sh](./kubernetes/10_build_dima.sh) and [11_deploy_dima.sh](./kubernetes/11_deploy_dima.sh). Depending on which infrastructure components you want to deploy, run the rest of the bash scripts.

Make sure to also run [30_deploy_ingress.sh](./kubernetes/30_deploy_ingress.sh) which deploys the ingress controller and exposes your application at http://localhost:8080.