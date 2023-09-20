#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app/build/libs"
JAR_FILE="$PROJECT_ROOT/dev-0.0.1-SNAPSHOT.jar"


CURRENT_PID=$(pgrep -f $JAR_FILE)

echo "파일 실행"

# jar 파일 실행
echo "$CURRENT_PID"

echo "$JAR_FILE"

java -jar $JAR_FILE



