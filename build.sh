#!/bin/bash

mvn clean package -Dmaven.test.skip

docker build -t tla-document-digitization .

docker run -it tla-document-digitization -p 9000:9000