# Creating a workshop machine

## Prerequisits

> NOTE: These instructions are all written from the point of view of region `eu-west-2`. This is not required but please change each location of this region name if you choose to use a different one.

1. AWS account and developer command line credentials
    - Please follow along with AWS documentation if you have not yet created your own account.
    - Once you have an account set up, you will need to set up the [aws command line tool](https://docs.aws.amazon.com/cli/index.html).
1. A VPC
    - The AWS region you will be using needs an existing VPC in it for the application machine to be built in.
    - This can be checked via commandline: `aws ec2 describe-vpcs`.
    - To create one via commandline if needed use: `aws ec2 create-vpc --cidr-block 10.0.0.0/16`
1. `md5.2xlarge` machine type available
    - It is common for AWS to limit the larger machine types for newer accounts. You can check your machine type limits following [these](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-resource-limits.html) instructions.
    - If you do need to request a limit increase this can take a few days so be sure to request in the same region as your VPC to limit requesting a second time.
 1. The [mozilla sops](https://github.com/mozilla/sops) utility installed. 
    - You can use `homebrew` on OS X for this - `brew install sops`
 1. gpg installed and the `featurecreeps` pgp key imported. This lives in the keybase team drive at /keybase/team/featurecreeps/gpg/featurecreeps.asc.
    - `gpg --import /keybase/team/featurecreeps/gpg/featurecreeps.asc`
    

## Creating the machine infrastructure

1. Create a running instance of type `md5.2xlarge`
    - 
    ```
    docker-machine create --driver amazonec2 --amazonec2-region eu-west-2 \
    --amazonec2-open-port 8080 --amazonec2-open-port 80 \
    --amazonec2-open-port 5601 --amazonec2-open-port 3000 \
    --amazonec2-open-port 9090 --amazonec2-open-port 9411 \
    --amazonec2-open-port 80 --amazonec2-open-port 8080 \
    --amazonec2-open-port 8081 --amazonec2-open-port 8082 \
    --amazonec2-open-port 8083 --amazonec2-open-port 8084 \
    --amazonec2-open-port 8085 --amazonec2-open-port 8086 \
    --amazonec2-instance-type m5.2xlarge --amazonec2-root-size 200 \
    o11y-workshop
    ```
Some issues may run into:
- `Error setting machine configuration from flags provided: amazonec2 driver requires AWS credentials configured with the --amazonec2-access-key and --amazonec2-secret-key options, environment variables, ~/.aws/credentials, or an instance role`
  - The docker-machine command does not read AWS credentials from the usual location so look to use either aws-vault (advanced) or export your `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, and `AWS_DEFAULT_REGION` credentials before running additional commands.
- If you get an error about ENI compatability add this flag to the command: `--amazonec2-ami ami-068f09e337d7da0c4`
- If you use `aws-vault` to protect AWS creds add this before the above command: `aws-vault exec ninedemons-admin_role -- `
- If you have any issues bringing up an instance or have previously done so, you may need to clean out the docker-machines by running `docker-machine rm <machine name>` and/or removing files found at `~/.docker/machine/machines/` and/or clean out the AWS Keys which can be found by running `aws ec2 describe-key-pairs`
- If you haven't provisioned a machine in this region before, Amazon may need to validate your request first. This usually only takes a few minutes, but it can result in your instance request hanging (you won't be able to talk to it). If that happens, use `docker-machine rm <machine name>` to remove the instance then re-run `docker-machine create`.

## Installing the application on the machine

1. Set your shell environment to use the new instance to be able todo all the docker commands you would normally use:
    ```
    eval $(docker-machine env o11y-workshop)
    ```
1. The machine will need one additional setting to successfully run Elastic Search. To set this, first ssh to the machine:
    ```
    docker-machine ssh o11y-workshop
    ```
    and then run the following command:
    ```
    sudo sysctl -w vm.max_map_count=524288
    ```
    before exiting back to your local machine.
1. (Optional) Set your shell environment to have environment variables used by the docker images. For example, the honeycomb key.
    ```bash
    cd ./stack/stack-full
    export $(sops -d .sops.env)
    ```
    You should have the variables in your shell now. To check :
    ```bash
    env | fgrep HONEYCOMB
    ```
     
1. To run the application, cd into `./stack/stack-full` and run `docker-compose up --build --detach`.
    - `-d` runs the application in detached mode to allow you further use of your command line. If you remove the `-d` your application will log straight to the command line window you ran `up` in.
    - `--build` rebuilds the application each time you run `up`. If you remove this it may speed up the build time but also may miss any local changes you introduce.

### Generating traffic
> Note: you will need to have go and vegeta installed on your machine before proceeding

1. To run the traffic generator to upload traffic :
```
go run main.go -d ~/work/flickrscrape/result -u http://<public_ip_of_docker_machine>:8080/api/images | \
  vegeta attack -rate=10/m -lazy -format=json -duration=30s | \
  tee results.bin | \
  vegeta report
```

### Cleaning up the infrastructure

1. To remove the machine, do `docker-machine rm o11y-workshop`

>NOTE: 
> 
> If you plan to do further work with this instance, you can run `docker-machine stop o11y-workshop` instead of rm. Be aware that when you start the instance again (using `docker-machine start o11y-workshop`) the IP address will have changed as it is assigned dynamically at startup. 
> 
> You will also need to regenerate certificates for the machine, using `docker-machine regenerate-certs o11y-workshop`.

## Accessing the machine

### Accessing the machine locally

1. Set your shell environment to use the new instance:
```
eval $(docker-machine env o11y-workshop)
```

1. docker-machine ssh o11y-workshop

### Accessing the machine remotely

The owner of the machine must provide other users with both the IP and the private key.

1. Machine owner can set their shell environment to use the instance:
```
eval $(docker-machine env o11y-workshop)
```

1. To get the public IP of your instance:
```
docker-machine ip o11y-workshop
```

1. To get the private key for the machine:
```
cat $(docker-machine inspect -f {{.Driver.SSHKeyPath}} o11y-workshop)
```

1. Save that private key in a known location on your computer (e.g. home/workshopkey)

#### *nix and mac ssh

1. Run the ssh command while specifically identifying the private key to use:
```
ssh -i ~/workshopkey ubuntu@ip.of.docker.machine
```
> Note: You may need to confirm your ssh key file is set to secure enough permissions, you can review common settings here: https://superuser.com/questions/215504/permissions-on-private-key-in-ssh-folder

#### Windows ssh

If you have windows 10, you can turn on an optional feature of SSH Client:
https://www.maketecheasier.com/use-windows10-openssh-client/

> Note: you may need to change ownership of your key to a more secure setting as described here: https://superuser.com/questions/1296024/windows-ssh-permissions-for-private-key-are-too-open

If you do not have Windows 10 or would prefer to use PuTTY you can follow these instructions instead:
https://support.rackspace.com/how-to/log-into-a-linux-server-with-an-ssh-private-key-on-windows/

HOW TO USE WINDOWS 10: 
changing key ownership: https://superuser.com/questions/1296024/windows-ssh-permissions-for-private-key-are-too-open
