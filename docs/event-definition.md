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
        * a start time
        * an end time 
        * a duration
    * optional:
        * a type (HTTP call, message, cron job, ..)
        * reference to other events
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