#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app/build/libs"
JAR_FILE="$PROJECT_ROOT/dev-0.0.1-SNAPSHOT.jar"


CURRENT_PID=$(pgrep -f $JAR_FILE)

echo "$CURRENT_PID"

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]; then
  echo "현재 실행중인 애플리케이션이 없습니다"
else
  echo "실행 중인 애플리케이션 종료 "
  kill -9 $JAR_FILE
fi

echo "파일 실행"

# jar 파일 실행
CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$CURRENT_PID"

nohup java -jar $JAR_FILE



