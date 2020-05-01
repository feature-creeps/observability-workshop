# event

## definition

* encompasses one unit of work being executed by one process
    * examples:
        * HTTP call to a service endpoint
        * message received via message broker (listener triggered)
        * cron job triggered
        * file/dir listener triggered
* properties
    * required:
        * unique id
        * a starting timestamp
        * a duration
    * optional:
        * a type (HTTP call, message, cron job, ..)
        * reference to other events (parent id, trace id, ...)
        * an arbitrary number of domain specific fields

### challenges

* all the properties of an event are domain specific
    * either in value format
        * eg how a date is represented
    * or in their fields
        * each non "default" / "technical" field is domain specific
* an event can only be published once a load of work has finished
    * an endless loop might never emit an event
    * event publishing might fail
    * we must assure that there is nothing being executed after publishing of the event
    
    
    
### events vs logs

* you are not guaranteed to get a log line for every event that happens
  * events are thorough
* you probably get multiple log lines for singular events
  * contain a lot redundant data
* logs are arbitrarily positioned in your code base
* more relevant & uniform structure
* context (fields) limited to the progress until the log line is written
* log lines might lie: "transformation successful" just before the termination of the transaction, but it does actually fail after that
* event has transaction encapsulating meta data:
  * duration, start/end time, 
* log is a point in time, event encapsulates a transation/request/workload/..
* 