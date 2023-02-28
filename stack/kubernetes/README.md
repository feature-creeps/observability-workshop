# kubernetes

This folder contains the scripts and configurations for the deployment to the google cloud project `olly-2021-k8s-migration`. Ask Benny for access. 

As a platform we use a managed k8s cluster: GKE (Google Kubernetes Engine). For the setup, check out [10_setup_infrastructure.sh](10_setup_infrastructure.sh).

A container registry is available with the creation of the google cloud project, in this case: `gcr.io/olly-2021-k8s-migration`.

A static ip with the name "static-ip" was created manually once: `gcloud compute addresses create static-ip --region europe-west1`
The domain "o11y.fans" was created manually and is being assigned to the static ip through an A name record. Subdomains through a wildcard CNAME record.

## ci/cd

For the build and deploy pipeline we make use of github actions, see [.github](../../.github). 

In order to be able to deploy using github actions iam was configured as in [00_setup_iam.sh](00_setup_iam.sh)

## olly tools

The observability tooling is making use of the following:

* istio
* EFK
* prometheus, grafana
* jaeger

For setup details check out the scripts in [kubernetes](./)

## application

The deployment of the dima docker images is done with [kustomize](https://kustomize.io/), see [kubernetes/application](./application).

## required binaries

| binary    | tested with version |
| ---       | ---                 |
| gcloud    | 2023.02.13          |
| kubectl   | 1.26.1              |
| helm      | 3.10.2              |
| kustomize | 5.0.0               |
