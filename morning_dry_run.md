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

8. ## SEND TRAFFIC ##

## Activity

1. Navigate participant to website (`## NEED URL ###`)
2. "For the next 10 minutes you will get to explore the site we will be using today. But there is one small task you need to complete as well. To get to know each other a bit better we are going to start with sharing some pictures. Pick either a personal picture or feel free to use one from google on a relevant topic to upload to the site and we will share a quick intro in about 10 minutes."
3. "Let's go around and share our name, current role, reason for attending this workshop and the story behind your picture"
---------------
4. "In the next 10 minutes create a written down modal of this website to aid your testing. You can do this by yourself or as a pair/small group, but please make sure it is in written format."
5. "Now let's spend about 15 minutes questioning this model. What risks would we most want to validate further? What questions do we have about architecture based on what we can see in the browser?"
---------------
6. "You actually do have access to where this is all running. You can now ssh into the box where this is being hosted and begin to poke around. Our goal is to explore the system and generate a technical modal of the system in the next 20 or so minutes" (ssh command: `## NEED COMMAND ##`)
7. "How does this technical modal interact with the user facing one? What questions have become more pressing? Any questions become moot given new information? What tools do you have access to now? Why so many tools, which is used for what purpose?"
---------------
8. "For the remainder of the morning we are going to use the tools provided to try and capture what 'normal' might be for this system. How might you put a marker in the sand so that you will know if something funny starts happening?"s


## Things to validate and track

- Timing
- Clarifying questions
