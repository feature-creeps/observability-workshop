# Morning dry run

## Setup

1. Launch an EC2 instance using the free tier for image type `Ubuntu Server 18.04 LTS (HVM), SSD Volume Type`

2. ssh into the EC2 instance:
```
ssh -i "{{AS-CREATED}}.pem" ubuntu@{{Public DNS (IPv4)}}
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
> NOTE: This takes a long time (~45min?!) on t2 free tier box.


## Activity

1. Navigate participant to website (``)
1. "For the next 10 minutes you will get to explore the site we will be using today. But there is one small task you need to complete as well. To get to know each other a bit better we are going to start with sharing some pictures. Pick either a personal picture or feel free to use one from google on a relevant topic to upload to the site and we will share a quick intro in about 10 minutes."
1. "Let's go around and share our name, current role, reason for attending this workshop and the story behind your picture"
---------------
1. "In the next 10 minutes create a written down modal of this website to aid your testing. You can do this by yourself or as a pair/small group, but please make sure it is in written format."
1. 


## Things to validate and track

- Timing
- Clarifying questions
