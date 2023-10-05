#!/bin/bash

if kind get clusters | grep kind > /dev/null; then
    echo Deleting kind cluster ...
    kind delete cluster
else
    echo Nothing to tear down, exiting ...
    exit 0
fi