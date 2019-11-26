* start up a vm with at least 16gb of memory
* as root execute:
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

# increase max map count
sysctl -w vm.max_map_count=262144

# create user
useradd -m -G docker -s /bin/bash olly
echo "olly ALL=(ALL:ALL) NOPASSWD:ALL" >> /etc/sudoers

# add ssh pub key
runuser -l olly -c 'mkdir /home/olly/.ssh'
runuser -l olly -c 'echo "<insert key here>" > /home/olly/.ssh/authorized_keys' 

# checkout repo
runuser -l olly -c 'git clone https://github.com/feature-creeps/observability-workshop.git $HOME/observability-workshop'

# start stack
runuser -l olly -c 'docker-compose -f $HOME/observability-workshop/stack/stack-full/docker-compose.yml up --build -d'
```

To reset the stack every night we add a cronjob:
```bash
# add a reset.sh script with the following content and chmod +x
# #!/bin/bash
# 
# docker-compose --project-directory /home/olly/observability-workshop/stack/compose/ \
# 	-f /home/olly/observability-workshop/stack/compose/docker-compose-level-9.yml \
# 	down -v --remove-orphans
# 
# docker-compose --project-directory /home/olly/observability-workshop/stack/compose/ \
# 	-f /home/olly/observability-workshop/stack/compose/docker-compose-level-9.yml \
# 	up --build -d

# edit cronjobs
crontab -e

# add this line
# 0 0 * * * /home/olly/reset.sh
```