# Logging Tasks & Solutions

For all tasks use the `logs-*` index pattern.

##### Query: How many successful image flips between 8AM-10Am? (together)
* lucene query:
```bash
service.keyword : imageflip AND message.keyword : "Successfully flipped image" 
```
* lucene time span: 8AM - 10AM

##### Query: How many successful image flips today?
* lucene query:
```bash
service.keyword : imageflip AND message.keyword : "Successfully flipped image" 
```
* lucene time span: today

##### Query: How many failed image transformations in the last 3 hours?
* lucene query:
```bash
service.keyword : "imageorchestrator" AND message.keyword : "Failed transforming image"  
```
* lucene time span: last 3 hours

##### Visualize: image flips over time.
* visualisations > create > line > logs-*
* lucene query:
```bash
service.keyword : imageflip AND message.keyword : "Successfully flipped image" 
```
* Y-axis: Count
* Buckets:
  * X-axis:
    * aggregations: Date Histogram

##### Visualize : cumulative image flips over time.
* visualisations > create > line > logs-*
* lucene query:
```bash
service.keyword : imageflip AND message.keyword : "Successfully flipped image" 
```
* Y-axis: 
  * Cumulative Sum
    * Metric: Custom metric
    * Aggregation: Count
* Buckets:
  * X-axis:
    * aggregations: Date Histogram

##### Dashboard: Add Both visualizations to a dashboard
* save visualisations from above with names
* dashboard > create new
* add existing visualisation
  * select both from above
* save dashboard with name

##### Dashboard : Configure dashboard with control selection by image type (mime type).
* visualisations > create > control
* options list > add
  * control label: "image type"
  * index pattern: logs-*
* save as "image type selector"
* dashboard > select saved dashboard from before
  * add > "image type selector"
  * save