
# Overview
The following instructions are far people who have followed the instructions previously on how to setup an AWS instance which has at least 16GB of memory. 
Instructions on how to create an amazon instance of this size can be can be found in https://github.com/charrett/observability-workshop/blob/master/docs/create_vm_in_aws.md

# Assumptions: 
1) You have followed the instructions in 'create vm in aws' and have at least 16GB of Memory 
1) You are logged into your AWS instance. (Not your local machine) 
2) You have a honeycomb Key (If you don't please see one of the workshop owners) 
3) Your user id is ubuntu. (If this is not the case where you see ubuntu in the instructions below replace with your user id)
4) You don't have root access and will be using sudo 

## Startup your AWS instance from your local machine
eval $(docker-machine env o11y-workshop)
docker-machine ssh o11y-workshop

## Install docker
<question for abby is this needed isn't docker already installed?> 
1) Get your instance up to date 
sudo apt-get update
2) Install some stuff need for the workshop 
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
3) Download Docker gpg 
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - 
4) Download the latest docker instance for ubuntu 
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
4) Refresh and update to make sure its all latest everything 
sudo apt-get update
5) Download docker compose 
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
6)  Change the directory permissions 
sudo chmod +x /usr/local/bin/docker-compose

## increase max map count for elastic search
sudo sysctl -w vm.max_map_count=262144

## set HONEYCOMB enviornment variable
export HONEYCOMB_KEY=<ask workshop owners for this>
(check the key is set properly) 
echo $HONEYCOMB_KEY 

## checkout repo
sudo git clone https://github.com/feature-creeps/observability-workshop.git $HOME/observability-workshop

## install and start stack
sudo $HOME/observability-workshop/start-stack-in-level.sh 9

## Check Stack is working
check the IP Address 

## Stopping the Stack 
sudo docker-compose --project-directory /home/ubuntu/observability-workshop/stack/compose/ -f /home/ubuntu/observability-workshop/stack/compose/docker-compose-level-9.yml down -v --remove-orphans

## Restarting the Stack 
sudo docker-compose --project-directory /home/ubuntu/observability-workshop/stack/compose/ \
	-f /home/ubuntu/observability-workshop/stack/compose/docker-compose-level-9.yml \
	up --build -d
