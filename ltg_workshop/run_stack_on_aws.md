# Creating a workshop machine

## Machine operations

### Creating the machine infrastructure

1. Your AWS region will need an existing VPC in it. This can be checked via commandline: `aws ec2 describe-vpcs`. To create one via commandline if needed use: `aws ec2 create-vpc --cidr-block 10.0.0.0/16`

1. Create a running instance of type `md5.2xlarge` and in region `eu-west-2` use this command. You may need to shift these variables depending on your account allotment.
```
docker-machine create --driver amazonec2 --amazonec2-region eu-west-2 \
--amazonec2-open-port 8080 --amazonec2-open-port 5601 --amazonec2-open-port 3000 \
--amazonec2-open-port 9090 --amazonec2-open-port 16686 --amazonec2-open-port 9411 \
--amazonec2-instance-type m5.2xlarge --amazonec2-root-size 200 \
o11y-workshop
```

Depending on your local setup you may need to provide additional information. For example, 

- If you use `aws-vault` to protect AWS creds add this before the above command: `aws-vault exec ninedemons-admin_role -- `
- If you get an error about ENI compatability add this flag to the command: `--amazonec2-ami ami-068f09e337d7da0c4`
- If you have any issues bringing up an instance or have previously done so, you may need to clean out the docker-machines found at `~/.docker/machine/machines/` and or clean out the AWS Keys which can be found by running `aws ec2 describe-key-pairs`

### Installing on the machine

1. Set your shell environment to use the new instance:
```
eval $(docker-machine env o11y-workshop)
```

1. The machine will need one additional setting to successfully run Elastic Search. To set this, first ssh to the machine:
```
docker-machine ssh o11y-workshop
```
and then run the following command:
```
sudo sysctl -w vm.max_map_count=262144
```
before exiting back to your local machine.

1. Now you can do all the docker stuff you would running locally. So `docker-compose up` in `observability-workshop/stack/stack-local-default`

### Generating traffic

1. To run the traffic generator to upload traffic :
```
go run main.go -d ~/work/flickrscrape/result -u http://<public_ip_of_docker_machine>:8080/api/images | \
  vegeta attack -rate=10/m -lazy -format=json -duration=30s | \
  tee results.bin | \
  vegeta report
```

### Cleaning up the infrastructure

1. When done do `docker-machine rm o11y-workshop`


## Accessing the machine

### Running the machine locally

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
