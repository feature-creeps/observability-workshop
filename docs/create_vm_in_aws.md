# Creating a virtual machine using AWS for the purposes of installing the observability workshop stack

## Prerequisites

> NOTE: These instructions are all written from the point of view of Amazon region `eu-west-2`. This is not required but please change each location of this region name if you choose to use a different one. If you encounter permissions issues, `sudo -i` will let you operate all the rest of the commands as a super user without having to set permissions on each request.

1. AWS account and developer command line credentials
    - Please follow along with AWS documentation if you have not yet created your own account.
    - Your default region is at the top right of the AWS console page next to your account name. 
    - Once you have an account set up, you will need to set up the [aws command line tool](https://docs.aws.amazon.com/cli/index.html).
    - Somewhere along the line you will need to set environment variables with your AWS Access Key ID and Secret Key ID. You can find your Access Key ID on the [security_credentials](https://console.aws.amazon.com/iam/home#/security_credentials) page but you need to have saved your secret access key somewhere safe, or [generate new credentials](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).
2. A VPC (Virtual Private Cloud)
   - The AWS region you will be using needs an existing VPC in it for the application machine to be built in.
   - By default a VPC is created when you create a AWS account. This can be checked via command-line:
    ``` bash
    aws ec2 describe-vpcs
    ```
    - If you wish to create a second VPC you can do so via command-line:
    ``` bash
    aws ec2 create-vpc --cidr-block 10.0.0.0/16
    ```
3. An AMI (Amazon Machine Instance)
    - AMI's are region dependent. [Pick a Ubuntu flavour](https://cloud-images.ubuntu.com/locator/ec2/) Note: type in your region in the search, picking the first "bionic" one seems to work. Copy the AMI-ID, just the value of it, not the link address.

4. - use machine type `m5.2xlarge`
    - It is common for AWS to limit the larger machine types for newer accounts. You can check your machine type limits following [these](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-resource-limits.html) instructions.
    - If you do need to request a limit increase this can take a few days so be sure to request in the same region as your VPC to limit requesting a second time.
5. Honeycomb key
   This stack will startup with or without a Honeycomb key. If you wish to integrate that data from the stack into Honeycomb you will need a key. Right now you need to ask one of the organisers for a key or signup for your own [free account](https://ui.honeycomb.io/signup/free) from which you can get your own key.
6. Docker Machine
Download Docker and Docker Machine. Docker Machine allows you to install the docker engine on your virtual host of choice and then manage these hosts using docker-machine commands.  

## Creating the machine infrastructure
1. On your local machine, open up a shell (in Mac the application is called terminal).
2. Create a running instance called o11y-workshop, of type `m5.2xlarge` using command docker-machine.
> Note. Replace ami-068f09e337d7da0c4 with the name of your ami instance.

 ``` bash
    docker-machine create --driver amazonec2 --amazonec2-region us-west-2 \
    --amazonec2-ami ami-06ffade19910cbfc0 \
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
- If you have any issues bringing up an instance or have previously done so, you may need to clean out the docker-machines by running `docker-machine rm o11y-workshop` and/or removing files found at `~/.docker/machine/machines/` and/or clean out the AWS Keys which can be found by running `aws ec2 describe-key-pairs`
- If you haven't provisioned a machine in this region before, Amazon may need to validate your request first. This usually only takes a few minutes, but it can result in your instance request hanging (you won't be able to talk to it). If that happens, use `docker-machine rm o11y-workshop` to remove the instance then re-run `docker-machine create`.

## Installing the application on the machine
Congratulations, you have now setup an AWS instance. Next steps will be to install the workshop stack on the instance. Instructions to do so are in [install_stack_on_aws](https://github.com/charrett/observability-workshop/blob/master/docs/install_stack_on_aws.md)


## Cleaning up the infrastructure

1. To remove the machine, do `docker-machine rm o11y-workshop`

>NOTE:
>
> If you plan to do further work with this instance, you can run `docker-machine stop o11y-workshop` instead of rm. Be aware that when you start the instance again (using `docker-machine start o11y-workshop`) the IP address will have changed as it is assigned dynamically at startup.
>
> You will also need to regenerate certificates for the machine, using `docker-machine regenerate-certs o11y-workshop`.
