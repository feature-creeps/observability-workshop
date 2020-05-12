* start up a vm with at least 16gb of memory for instructions on how to do this read on to create a vm on aws
The AWS instance does not recommend root access. Instead use sudo before the following commands. 
```bash
# install docker
 apt-get update
apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
apt-get update
apt-get install -y docker-ce docker-ce-cli containerd.io
curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# increase max map count for elastic search
sysctl -w vm.max_map_count=262144


# checkout repo
git clone https://github.com/feature-creeps/observability-workshop.git $HOME/observability-workshop

# start stack
$HOME/observability-workshop/start-stack-in-level.sh 9
``
