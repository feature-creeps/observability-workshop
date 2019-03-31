# LTGWorkshops

## Open questions

* set up https://tlk.io/ ?
* Cheatsheet on terms (USE vs RED etc)
* Talk to MoT about networking for this workshop
* Work on application UI (nice to have)

## High level prep schedule (based on: [workshop_schedule.md](./workshop_schedule.md))

April 1: Dry run morning
* ~~A working stack~~
  * Make kibana start with an index already set up
  * Hook in image orchestrator services..accepts requests with some encoded series of operations such as “rotate through 90 degrees, transform to PNG, grayscale, rotate through -270 degrees,...”
* ~~Documented way to start up the stack ([run the stack on an aws ec2 instance](./run_stack_on_aws.md))~~
* ~~A way to add load to the system~~
  * ~~Documented (https://github.com/sneakybeaky/o11y-traffic#usage-feed-targets-into-vegeta)~~
  * Nice to have, make the traffic a “pre-canned” experiences through the application
* Cheat sheets for how to explore the architecture
  * e.g. commands (docker cli, prom query, kibana query, grafana)

April 20: Dry run the observable fault
* Identified the fault we want to inject
  * Requirements for issue: Visible in metrics, logs, and traces
* Documented way to create it
* Documented way to fix it
* Example graphs/alerts to identify the issue

May 15: Dry tun the less observable fault
* Identified the fault we want to inject
  * Requirements for issue:
    * Not able to be debugged from UI
    * Not able to be identified in metrics, logs, or traces
    * A pretty clear gap in observability
    * Pretty obscure probably as that is when observability is most necessary (but needs to be something common enough that we it’s not “works on my machine”)
  * Possible ideas:
    * Can we create a service that turns into a black hole pretty frequently? The idea being this emmits no diagnostics and fails in a frustrating manner. For example, accepts incoming requests but never returns.
    * Issue is exacerbated on a single node/vm
    * docker chaos degrading traffic between a couple nodes
* Documented way to create it
* Documented way to fix it
* Example graphs/alerts to identify the issue
* Example how to increase observability to track issue

June 10: Full dry run

June 26: LTG :D
