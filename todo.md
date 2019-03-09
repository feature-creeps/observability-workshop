# TODO

* add choreo
* update readme
* add tracing stack
* migrate to k8s

## low prio

* make application runnable with or without mongodb
* have a default image in the app (otherwise gets an error when loading random)?

## open points

* possibly implement a more interesting application
* multi node setup (with ops stack and multiple client stacks) ideally with service discovery (solved by k8s)

# LTGWorkshops

## TODO

* Readme update about how to start the stack
* set up https://tlk.io/ ?
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

## High level prep schedule

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
