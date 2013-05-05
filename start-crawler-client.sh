#!/bin/sh

cd $(dirname $0)/gft-crw-client/
exec mvn exec:exec
