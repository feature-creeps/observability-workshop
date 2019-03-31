# Morning dry run

## Setup

1. [run the stack on an aws ec2 instance](./run_stack_on_aws.md)
2. ## SEND TRAFFIC ##

## Activity

1. Navigate participant to website (`## NEED URL ###`)
2. "For the next 10 minutes you will get to explore the site we will be using today. But there is one small task you need to complete as well. To get to know each other a bit better we are going to start with sharing some pictures. Pick either a personal picture or feel free to use one from google on a relevant topic to upload to the site and we will share a quick intro in about 10 minutes."
3. "Let's go around and share our name, current role, reason for attending this workshop and the story behind your picture"
---------------
4. "In the next 10 minutes create a written down modal of this website to aid your testing. You can do this by yourself or as a pair/small group, but please make sure it is in written format."
5. "Now let's spend about 15 minutes questioning this model. What risks would we most want to validate further? What questions do we have about architecture based on what we can see in the browser?"
---------------
6. "You actually do have access to where this is all running. You can now ssh into the box where this is being hosted and begin to poke around. Our goal is to explore the system and generate a technical modal of the system in the next 20 or so minutes" (ssh command: `## NEED COMMAND ##`)
    * "How could we find out what is running on the server?"
        * `htop` should reveal that docker processes are running.
    * "What is docker running?"
        * `docker ps` will list the running images and even the name gives a lot of info
    * "Which are software application services and which are platform tools?"
        * Google will help with this, also the pattern that all software has `stack-local` in the name
    * "Could we use the tools to help us undertand how the software services talk to each other?"
        * OpenZipkin: Should have dependency graph
        * Kibana: Can see logs by container name, learn how to add columns and to include/exclude items from the search quickly


7. "How does this technical modal interact with the user facing one? What questions have become more pressing? Any questions become moot given new information? What tools do you have access to now? Why so many tools, which is used for what purpose?"
---------------
8. "For the remainder of the morning we are going to use the tools provided to try and capture what 'normal' might be for this system. How might you put a marker in the sand so that you will know if something funny starts happening?"


## Things to validate and track

* Timing
* Clarifying questions
