#!/bin/bash

if k3d cluster ls | grep o11y-stack > /dev/null; then
    echo Deleting k3d cluster ...
    k3d cluster delete o11y-stack
else
    echo Nothing to tear down, exiting ...
    exit 0
fi