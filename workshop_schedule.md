# Workshop

## Learning objectives

* Explain what observability is and why it is valuable
* How to question(/test) not only applications, but system risks
* Describe when to use different types of telemetry
* Understand common risks of a distributed system

## Schedule

### Morning

Goals:
* Confidence with the application and provided platform tools
* Understanding "normal" for the system and application
* Define baseline for the application and system

Activities:
1. Intro
1. Model the application from the user interface
1. Question the application model for risks
1. Model the system in technical architecture
1. Question the system model for risks
1. Identify what observability information is needed to evaluate the risks identified
1. Morning retro

### Afternoon

Goals:
* Experience debugging a distributed system
* Describe the difference between a highly observable service and a less observable service / system
* Identify how to add observability to a service / system

Activities:
1. Debug a highly observable issue
1. Debug a poorly observable issue
1. Improve observability for 2nd issue
1. Wrap up


## Activity details

#### Intro

> TBD

#### Model the application

> Given the URL for the website, participants generate a model of what users can do

#### Question the application model

> Focus points given the application model:
> * Questions about the behaviour
> * What would be critical issues for our users

#### Model the system

> Given access to docker CLI, participants generate a model of the system and platform architecture

#### Question the system model

> Focus points given the system model:
> * Questions about the behaviour
> * Changes to risk profile from the application model

> Focus points given the platform model:
> * Why do we have multiple tools
> * Which tool can be used to answer each question from part 3 (Question the application model)

#### Define normal for the application and system

> Given a diverse yet consistent work load, participants generate a way to visualise "normal" system behaviour


#### Morning retro

> TBD


#### Debug a highly observable issue


#### Debug a poorly observable issue


#### Improve observability for 2nd issue


#### Wrap up


## TODOs

* Readme update about how to start the stack
* Cheatsheet on terms (USE vs RED etc)
* Cheatsheet on commands (docker cli, prom query, kibana query, grafana)
