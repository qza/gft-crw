#!/bin/sh

cd $(dirname $0)/gft-crw-store-web/
exec mvn jetty:run
