1. Launch an EC2 instance using image type `Ubuntu Server 18.04 LTS (HVM), SSD Volume Type` and size `md5.2xlarge`.
> Note: Make sure to have a useable .pem either generated and downloaded or selected during creation

2. ssh into the EC2 instance:
```
ssh -i "{{file.pem}}" ubuntu@{{Public DNS (IPv4)}}
```

3. Install docker:
```
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update
apt-cache policy docker-ce
sudo apt-get install -y docker-ce
sudo service docker start
```

4. Install docker-compose:
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

5. install codebase:
```
sudo apt install git-all
git clone https://github.com/xellsys/observability-workshop.git

```

6. In order to support running elastic search locally, begin by setting the max_map per [this github comment](https://github.com/docker-library/elasticsearch/issues/98#issuecomment-218071315):
```
sudo sysctl -w vm.max_map_count=262144
```

7. Start the app:
```
cd observability-workshop/stack/stack-local-default/
docker-compose up
```