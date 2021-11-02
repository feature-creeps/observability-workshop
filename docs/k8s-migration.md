# k8s migration

Work mode details are on a [miro board](https://miro.com/app/board/o9J_l_U-TRk=/). Ask Benny for access.

## Infrastructure

We use the services of google cloud. A gc project was created: `olly-2021-k8s-migration`. Again, just ask Benny for access. 

As a platform we use a managed k8s cluster: GKE (Google Kubernetes Engine). For the setup, check out [infrastructure.sh](../stack/infrastructure.sh) and [iam.sh](../stack/iam.sh).

A container registry is available with the creation of the google cloud project, in this case: `gcr.io/olly-2021-k8s-migration`.

## CI/CD

For the build and deploy pipeline we make use of github actions, see [.github](../.github).

## Deployment

The deployment of the docker images is done with [kustomize](https://kustomize.io/), see [deployment](../stack/deployment).