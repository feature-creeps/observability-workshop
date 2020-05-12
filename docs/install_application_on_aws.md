
start up a vm with at least 16gb of memory. Instructions on how to create an amazon instance can be found in <>

The AWS instance does not recommend root access. Instead use sudo before the following commands. 
```bash
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
``
