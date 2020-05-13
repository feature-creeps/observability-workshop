
start up a vm with at least 16gb of memory. Instructions on how to create an amazon instance of this size can be can be found in https://github.com/charrett/observability-workshop/blob/master/docs/create_vm_in_aws.md

# Assumptions: 
1) You have followed the instructions in 'create vm in aws' and have at least 16GB of Memory 
1) You are logged into your AWS instance. (Not your local machine) 
2) You have a honeycomb Key (If you don't please see one of the workshop owners) 
3) Your user id is ubuntu. (If this is not the case where you see ubuntu in the instructions below replace with your user id)
4) You don't have root access and will be using sudo 

# Overview

# install docker
sudo apt-get update
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - <question for abby is this needed isn't docker already installed?> 
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
sudo apt-get update
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# increase max map count for elastic search
sudo sysctl -w vm.max_map_count=262144

# checkout repo
sudo git clone https://github.com/feature-creeps/observability-workshop.git $HOME/observability-workshop

# start stack
$HOME/observability-workshop/start-stack-in-level.sh 9
