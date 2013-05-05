#!/bin/sh

cd $(dirname $0)/gft-crw-server/
exec mvn exec:exec
