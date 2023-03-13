# kubernetes

This folder contains the scripts and configurations for the deployment to the google cloud project `olly-2021-k8s-migration`. Ask Benny or Dominik for access. 

As a platform we use a managed k8s cluster: GKE (Google Kubernetes Engine). 

A static ip with the name "static-ip" was created manually once: `gcloud compute addresses create static-ip --region europe-west1`
The domain "o11y.fans" was created manually and is being assigned to the static ip through an A name record. Subdomains through a wildcard CNAME record.

A container registry is available with the creation of the google cloud project, in this case: `gcr.io/olly-2021-k8s-migration`.

## ci/cd

For the build, deliver and deploy pipeline we make use of github actions, see [.github](../../.github). Atm only the application deployment is automated through CI/CD.

## Scripts

The scripts are divided in lifecycle steps and described in the following. Details can be found within the scripts.

The scripts mainly make use of helm as a deployment mechanism. Thus, the single installations can be managed further with helm. To get an overview over the installed tooling refer also to `helm list -A`.

### Setup IAM

In order to be able to deliver and deploy using github actions iam was configured as in [00_setup_iam.sh](00_setup_iam.sh). It's only kept for documentary reasons.

### Setup Infrastructure

For the setup, check out [10_setup_infrastructure.sh](10_setup_infrastructure.sh). This needs to be run manually.

### Setup tools

The overview of the observability tooling can be found in the [project README](../README.md) or via helm. The scripts are divided via the namespaces of the installations. 

The scripts need to be run manually atm and may need some manual intervention, e.g. when creating the ES indices.

### Deploy Application

The deployment of the application. This is run with the CI/CD pipeline and can be used as general teardown.

### Tear down infrastructure

This simply deletes the whole GKE cluster.

## required binaries

| binary    | tested with version |
| ---       | ---                 |
| gcloud    | 2023.02.13          |
| kubectl   | 1.26.1              |
| helm      | 3.10.2              |
| kustomize | 5.0.0               |
