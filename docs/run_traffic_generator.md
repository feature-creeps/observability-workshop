# Generating traffic

In order to explore the observability of our system, we need realistic data flowing through our system.

## Default traffic generator

At startup of the application the [traffic generator](../stack/application/trafficgen/Dockerfile) is started. This will upload a number of free online images in bulk. Using the UI generation of transformation traffic can be started and configured. Also uploading of the default images can be triggered again through the UI.
