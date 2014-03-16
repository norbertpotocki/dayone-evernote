#!/bin/bash

echo "Installing dependencies in local maven repository"
echo "Downloading remark library"
curl -L https://bitbucket.org/OverZealous/remark/downloads/remark-0.9.3.jar > remark-0.9.3.jar

echo "Installing remark"
mvn install:install-file \
  -DgroupId=com.overzealous \
  -DartifactId=remark \
  -Dpackaging=jar \
  -Dversion=0.9.3 \
  -Dfile=remark-0.9.3.jar

rm remark-0.9.3.jar

echo "Dependencies installed"