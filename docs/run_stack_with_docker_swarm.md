# Deploying via Docker Swarm

In order to run this stack in a more "production like" but keep it as understandable as possible we have decided to use docker swarm to manage multiple replicas.

This doc will walk you through how to create and deploy to a single instance via docker swarm and allow scaling of the app.


## Creating the swarm

Docker swarm is a quorum based system where any number of computers are combined together to provide shared computer and controlled via a number of manager nodes.

At its simplest, we can create a swarm of a single node which is also a manager of itself. That is what we will do here.

These docs will follow along [this tutorial](https://docs.docker.com/engine/swarm/swarm-tutorial/) so in doubt, check that for more up to date references.


### Creating instances

You will need a server with docker and docker-compose on it. This can be created wherever you like but there are instructions for how to [create a server in AWS](./create_vm_in_aws) within this repo.

You may need to also provision the server further which you can find [instructions for here](./run_stack_on_ubuntu) as well.


### Initiating the swarm

Assuming you have ssh'd to the server you have decided to create the swarm on, follow [these instructions](https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/) to initiate the swarm


> Note: It is not required to use the `--advertise-addr` flag if only using a single node swarm



## Preparing docker images to deploy

### Create a swarm repository for the swarm

In order to deploy with swarm we must have all docker images stored in a repository. We will do this by generating a local repository within the server being used to run the application as well. This can be done by following [these instructions](https://docs.docker.com/registry/deploying/#run-a-local-registry).


### Getting all necessary images on the machine

From on the server you just made a member of a swarm, we must generate all docker images needed for the application.

This will require us to build any applications that require that by running:
```
docker-compose -f docker-swarm-level-{}.yml build
```

> Note: you may get some failures on the build command above, or the pull command below. This will be because it is failing on the images of the other type. This is normal and will resolve itself.

And to pull any that we do not build ourselves by running:
```
docker-compose -f docker-swarm-level-{}.yml pull
```


### Moving all local images to the swarm repository

Finally we need to push all the images to the local repository we created [above](#create-a-swarm-repository-for-the-swarm) by running:

```
docker-compose -f docker-swarm-level-{}.yml push
```


## Running the application stack

Use the following command to now run the application on the single node swarm:
```
docker stack deploy --compose-file docker-swarm-level-{}.yml o11ystack
```

## Validating the running stack

Start by running `docker stack services o11ystack` to validate that you have the right number of healthy replicas per service.

From here you can use many commands from `docker` as normal to interact with running containers as well as commands from `docker-compose` or `docker stack`
