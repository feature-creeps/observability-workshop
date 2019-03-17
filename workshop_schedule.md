# Workshop

## Learning objectives

* Explain what observability is and why it is valuable
* How to question(/test) not only applications, but system risks
* Describe when to use different types of telemetry
* Understand common risks of a distributed system

## Schedule

### Morning (9:00-12:00?)

Goals:
* Confidence with the application and provided platform tools
* Understanding "normal" for the application use

Activities:
1. Intro
1. Model the application from the user interface
1. --- 15 minute break ---
1. Question the application model for risks
1. Model the system in technical architecture
1. --- 15 minute break ---
1. Question the system model for risks
1. *(should have)* Identify what observability information is needed to evaluate the risks identified
1. Morning retro

### Afternoon (13:00-17:00?)

Goals:
* Understanding "normal" for the system throughput
* Experience debugging a distributed system
* Describe the difference between a highly observable service and a less observable service / system
* Identify how to add observability to a service / system

Activities:
1. Review/explore provided dashboards
1. Hosts introduce an issue in a highly observable service:
  * Identify bad behaviour in application
  * Generate a hypothesis on what could be cause based morning models
  * Use tools to evaluate hypothesis
  * Repeat until issue is identified
  * *hosts will then fix issue*
1. --- 15 minute break ---
1. Hosts introduce an issue in a poorly observable service:
  * Identify bad behaviour in application
  * Generate a hypothesis on what could be cause based morning models
  * Use tools to evaluate hypothesis
1. Group identifies what information is missing but would help
1. --- 15 minute break ---
1. Improve observability by adding the missing information 
1. Wrap up


## Activity details

#### Intro (15 min)

Goals: hands on immediately, get comfortable with each other

Everyone either bring a photo or use an commonly available image to represent their interests. Upload the photo and share it with their neighbour.

Possibly look to incorporate the whole room by asking that pair to then upload a common image and find another pair to share with. Continuing along until there is an image that the whole room agrees on.

> NOTE: We may have gray scale or other tools built into the platform, incorporate these within the round robin image uploading to uncover the full breadth of the site.

#### Model the application from the user interface (15 min)

Goals: Everyone has a physical representation of the application to interrogate

#### Question the application model for risks

> Focus points given the application model:
> * Questions about the behaviour
> * What would be critical issues for our users

#### Model the system in technical architecture

> Given access to docker CLI, participants generate a model of the system and platform architecture

#### Question the system model for risks

> Focus points given the system model:
> * Questions about the behaviour
> * Changes to risk profile from the application model

> Focus points given the platform model:
> * Why do we have multiple tools
> * Which tool can be used to answer each question from part 3 (Question the application model)

> QUESTION: how to introduce different types of models and which would be appropriate for this.




#### Define normal for the application and system

> Given a diverse yet consistent work load, participants generate a way to visualise "normal" system behaviour


#### Morning retro

> TBD


#### Hosts introduce an issue in a highly observable service

Set people off asap and do ~10 minute checkins for the duration.

Check-in #1 ask "what question are you trying to answer right now?". Goal will be to uncover if they are "clicking around randomly" or applying scientific exploration. Also can tackle the risk of too big a question and how to break that down into consumable bits.
