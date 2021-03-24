# Tracing Tasks Solutions

For solving the tasks use kibana and zipkin.

1. Query: Find a trace with imageflip (together)
    * in the zipkin UI
    * click on the + in the search bar
    * several tag key are offered
    * select serviceName
    * as value type in imageflip
    * click on the magnifier to search
    
2. Query: Make a transaction (image manipulation) and try to find your exact trace.
    * go to the DIMA UI
    * navigate to "Manipulate"
    * select an image and some manipulations
    * select persist and provide a name
    * take note of the name and or the id under which it was persisted
    * navigate to kibana and search for the imageId or the imageName
    * open a resulting document and copy the traceId value
    * go to the zipkin UI
    * enter the traceId in the top right trace id search

3. Analysis: Interpret your trace and tell us what you see.
    * look at the several services involved
    * look at the order of the manipulations happening
    * might this have an impact on your desired outcome?