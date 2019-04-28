# LTGWorkshops

## Open questions

* set up https://tlk.io/ ?
* Cheatsheet on terms (USE vs RED etc)
* Talk to MoT about networking for this workshop
* Work on application UI (nice to have)
  * More usable redirects after actions (instead of landing on API pages)
  * infinite scroll https://thirtyfour.org/

## High level prep schedule (based on: [workshop_schedule.md](./workshop_schedule.md))

~~April 1: Dry run morning~~ (migrated to: https://github.com/xellsys/observability-workshop/projects/1)
* ~~DryRun on how to SSH access from linux/windows~~
  * ~~"in an ideal world you would not need ssh to explore the architecture. let's see how that works out."~~
* ~~A working stack~~
  * ~~confirm traces are going thru to openzipkin~~
  * ~~Figure out why grafana is spitting out errors~~
  * ~~Hook in image orchestrator services..accepts requests with some encoded series of operations such as “rotate through 90 degrees, transform to PNG, grayscale, rotate through -270 degrees,...”~~
* ~~Documented way to start up the stack ([run the stack on an aws ec2 instance](./run_stack_on_aws.md))~~
* ~~A way to add load to the system~~
  * ~~Documented (https://github.com/sneakybeaky/o11y-traffic#usage-feed-targets-into-vegeta)~~
  * ~~Nice to have, make the traffic a “pre-canned” experiences through the application~~

~~Tech clean up:~~ (migrated to: https://github.com/xellsys/observability-workshop/projects/1)
 * ~~add orchastrator to the nav bar~~
 * ~~make display, upload, delete, orchastrator reply back with html page rather than just json (@Benny)~~
 * ~~Need to get the dependency graphing feature working within OpenZipkin (@Benny)~~
 * ~~Traffic generator for orchastrator (@Jon)~~
 * ~~Reintroduce image holder dashboard for Grafana - micrometer upgrade for guages because of label values needing to be hard coded? (@Benny)~~
 * ~~Make kibana start with an index already set up - maybe small script (@Benny)~~
 * ~~Move repo to shared ownership/org (@Jon)~~
 * ~~optional: make persist image tick box work for orchastrator - currently it always persists~~
 * ~~optional: introduce converter (png -> jpeg)~~
 * ~~optional: dropdown of imageholder images in image orchastrastor~~

~~April 20: Dry run the observable fault~~ (migrated to: https://github.com/xellsys/observability-workshop/projects/1)
* ~~Cheat sheets for how to explore the architecture~~
  * ~~e.g. commands (docker cli, prom query, kibana query, grafana)~~
* ~~Identified the fault we want to inject~~
  * ~~Requirements for issue: Visible in metrics, logs, and traces~~
* ~~Documented way to create it~~
* ~~Documented way to fix it~~
* ~~Example graphs/alerts to identify the issue~~

~~May 15: Dry tun the less observable fault~~ (migrated to: https://github.com/xellsys/observability-workshop/projects/1)
* ~~Identified the fault we want to inject
  * ~~Requirements for issue:
    * ~~Not able to be debugged from UI~~
    * ~~Not able to be identified in metrics, logs, or traces~~
    * ~~A pretty clear gap in observability~~
    * ~~Pretty obscure probably as that is when observability is most necessary (but needs to be something common enough that we it’s not “works on my machine”)~~
  * ~~Possible ideas:
    * ~~Can we create a service that turns into a black hole pretty frequently? The idea being this emmits no diagnostics and fails in a frustrating manner. For example, accepts incoming requests but never returns.~~
    * ~~Issue is exacerbated on a single node/vm~~
    * ~~docker chaos degrading traffic between a couple nodes~~
* ~~Documented way to create it~~
* ~~Documented way to fix it~~
* ~~Example graphs/alerts to identify the issue~~
* ~~Example how to increase observability to track issue~~

June 10: Full dry run

June 26: LTG :D
