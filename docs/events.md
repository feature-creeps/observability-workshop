# Using this codebase as a workshop

This code bases has been created as a playground. It is built with exploration in mind and in that focus it does not provide the same experience twice and also does not provide technical implementation details though of course you can derive them from the code. 

> Note: in the future we may look to build a progressive workshop around implementing these observability improvements so please reach out if that is of interest.

This is an opensource codebase under the [liencesed](../LICENSE) under [Apache 2.0](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)). As such you are free to take and build upon this, but of course as original creators we hope you will stay here and help us build the shared resources as well.

Below is a write up of our experiences running workshops using the environment this codebase provides.

## 2 day events

### Example events
* [Agile Testing Days 2019])(./events/2019-11-ATD/2019-11-03_AgileTestingDays (2-day).pdf)

### Overall

**Goal:**

To introduce the power of arbitrarily wide, context rich events and how to leverage them to explore a distributed environment

**Takeaways**

* Identify what can impact levels of observability
* Exposure to tools and techniques that are used when exploring observable systems
* Understand how the industry has moved towards observability

### Day 1

**Goals:**

* Gain experience with the provided tools
* Successfully navigate a fairly observable system
* Exposure to the data structures of logs, metrics and traces

**Activities:**

* Domain mapping: *Results in feature and risk assessment of the webapp*
* Architecture lecture: *Adding system architecture as influence of risk*
* Tooling lecture: *First explosure to type of data structures and visualisation*
* Full journey: *First look at tracing through to logs and higher level metrics*
* Deep dive on logs: *Explore events vs logs and how to work with the data*
* Deep dive on metrics: *Exposure to prometheus and how work with the data*
* Prep for day 2: *Generate / send public SSH key for logs on disk experience*

### Day 2

**Goals:**

* Confidence to get value regardless of current observability level
* Ideas on what to introduce next to improve observability
* Clear understanding of the value of each improvement

**Activities:**

Before each activity, run [`start-stack-in-level.sh #`](../start-stack-in-level.sh) to prepare the application.

* Level 0
    * Description: *Unstructured logs on the machine*
        * Prerequisite: *SSH key on the box*
    * Key focus: *Aggregation*
    * Task to try: *Check a log across multiple services*
* Level 1
    * Description: *Unstructured logs in aggregate*
    * Key focus: *Can search across servers / services but requires strict text searches and maybe regex*
    * Task to try: **

* Level 2
    * Description: *Structured logs*
    * Key focus: *Structure allows easier filtering*
    * Task to try: **

* Level 3
    * Description: *Event logging*
    * Key focus: *All the context of a transaction is in a single location*
    * Task to try: **

* Level 4
    * Description: *Uptime monitoring*
    * Key focus: *Can be alerted to start looking into the logs, but what does UP mean?*
    * Task to try: **

* Level 5
    * Description: *Technical / server monitoring*
    * Key focus: *Technical metrics can provide a lot of insight into system use, but also require gymnastics to apply to business context*
    * Task to try: **

* Level 6
    * Description: *Business context monitoring*
    * Key focus: *Readability and clarity on what matters to your business*
    * Task to try: **

* Level 7
    * Description: *Basic tracing*
    * Key focus: *Can see an error in the context of the larger request*
    * Task to try: **

* Level 8
    * Description: *Context rich traces*
    * Key focus: *Having context built in decreases copy / paste between tools*
    * Task to try: **

* Level 9
    * Description: *All data in a single location*
    * Key focus: *Building all visualisations off a single context rich / arbitrarily wide event provides flexibility and explorability*
    * Task to try: **
