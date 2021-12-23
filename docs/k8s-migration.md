# k8s migration

Remaining issues on the project board: https://github.com/orgs/feature-creeps/projects/6

## infrastructure

We use the services of google cloud. A gc project was created: `olly-2021-k8s-migration`. Ask Benny for access. 

As a platform we use a managed k8s cluster: GKE (Google Kubernetes Engine). For the setup, check out [00_setup_infrastructure.sh](../stack/kubernetes/00_setup_infrastructure.sh) and [00a_setup_iam.sh](../stack/kubernetes/00a_setup_iam.sh).

A container registry is available with the creation of the google cloud project, in this case: `gcr.io/olly-2021-k8s-migration`.

A static ip with the name "static-ip" was created manually once: `gcloud compute addresses create static-ip --region europe-west1`
The domain "o11y.fans" was created manually and is being assigned to the static ip through an A name record. Subdomains through a wildcard CNAME record.

## ci/cd

For the build and deploy pipeline we make use of github actions, see [.github](../.github).

## olly tools

The observability tooling is making use of the following:

* istio
* EFK
* prometheus, grafana
* jaeger

For setup details check out the scripts in [kubernetes](../stack/kubernetes)

## application

The deployment of the dima docker images is done with [kustomize](https://kustomize.io/), see [kubernetes/application](../stack/kubernetes/application).

## required binaries

* gcloud
* kubectl
* istioctl
