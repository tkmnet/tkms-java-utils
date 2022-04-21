#!/bin/sh

cd $(dirname $0)
./gradlew build
./gradlew publish
rsync -a tkms-java-utils/build/repo/ ./
