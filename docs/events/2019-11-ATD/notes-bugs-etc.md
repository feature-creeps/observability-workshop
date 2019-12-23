### Notes:
* Endpoint is null in many cases
* Review log levels (especially for 4xx/5xx)
* We have many exceptions in elastic for some time. Need to look into that. Might affect performance if not functionality.
* Change transformation_type_resize etc to transformationType = [rotate, resize, ...]
* However that is not possible for a combination multiple transformations.
* Add contextual tracing to elastic APM;
* Adapt level 7-9 to go from: tracing externally, tracing with context externally, tracing with context in kibana (APM).
* Fix frontend zipkin tracing.
* Fix transformationType field (only sets the last transformation as type even though there are possibly more transformations in one request (orchestrator)). (edited) 
* did well...leave times to play with things and hit level of technical well
* day 2 was more stressful because day 1 was so hard to learn the new tools (confirmed doing in this order was good tho because had tool experience out of the way) (edited) 
* idea: use concrete examples from first day brain storm of questions/risks in the 2nd day
* Idea: how about talking/discussing about the domain data after the domain maps?
* split between tech and biz focus/value/data was good...maybe need to keep poking this more
* include domain knowledge by testers brigding to observability
* start the workshop with this discussion
* rethink a11y for architecture diagrams


### WIP
* for boolean fields like "flip" assure that it is not only true and non-existent, but also false
* Align fields between structured logs and events.
  ** in branch align-log-fields
### DONE
* We have to add the index template to elasticsearch on startup (before any documents are written).
* Align credentials to services.
* If resize number put in, tickbox can be not clicked and resize is still in
  ** in branch remove-checkboxes