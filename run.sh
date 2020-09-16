#!/bin/bash

mvn install
docker build -t shakespeare-translator.jar .
docker run -p 5000:5000 shakespeare-translator.jar

