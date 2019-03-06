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
1. *(should have)* Identify what observability information is needed to evaluate the risks identified
1. Morning retro

### Afternoon

Goals:
* Experience debugging a distributed system
* Describe the difference between a highly observable service and a less observable service / system
* Identify how to add observability to a service / system

Activities:
1. Hosts introduce an issue in a highly observable service:
  * Identify bad behaviour in application
  * Generate a hypothesis on what could be cause based morning models
  * Use tools to evaluate hypothesis
  * Repeat until issue is identified
  * *hosts will then fix issue*

1. Hosts introduce an issue in a poorly observable service:
  * Identify bad behaviour in application
  * Generate a hypothesis on what could be cause based morning models
  * Use tools to evaluate hypothesis

1. Group identifies what information is missing (but would help)
1. Improve observability by adding the missing information
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
* Identify test runners and book in sessions
* Talk to MoT about networking for this workshop
* Hook in image orchestrator services
* Work on application UI (nice to have)
* Complete how to generate “normal traffic” (including readme to run it)
* Nice to have, make the traffic a “pre-canned” experiences through the application
* Identify observable issue and create a readme for how to introduce it
  * Requirements for issue: Visible in metrics, logs, and traces
* Identify non-observable issue and create a readme for how to introduce it
  * Requirements for issue:
    * Not able to be debugged from UI
    * Not able to be identified in metrics, logs, or traces
    * A pretty clear gap in observability
    * Pretty obscure probably as that is when observability is most necessary (but needs to be something common enough that we it’s not “works on my machine”)
  * Possible ideas:
    * Can we create a service that turns into a black hole pretty frequently? The idea being this emmits no diagnostics and fails in a frustrating manner. For example, accepts incoming requests but never returns.
    * Issue is exacerbated on a single node/vm
    * docker chaos degrading traffic between a couple nodes

## High level schedule

April 1: Dry run morning
* A working stack
* Clear way to start up the stack
* A way to add load to the system
* Cheat sheets for how to explore the architecture

April 20: Dry run the observable fault
* Identified the fault we want to inject
* Clear way to create it
* Clear way to fix it
* Example graphs/alerts to identify the issue

May 15: Dry tun the less observable fault
* Identified the fault we want to inject
* Clear way to create it
* Clear way to fix it
* Example graphs/alerts to identify the issue
* Example how to increase observability to track issue

June 10: Full dry run

June 26: LTG :D
