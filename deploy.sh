#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app/build/libs"
JAR_FILE="$PROJECT_ROOT/dev-0.0.1-SNAPSHOT.jar"

TIME_NOW=$(date +%c)

CURRENT_PID=$(pgrep -f $JAR_FILE)

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]; then
    echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다"
else
    echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료 "
    sudo kill -15 $CURRENT_PID
fi

# build 파일 복사 (선택적)
# echo "$TIME_NOW > $JAR_FILE 파일 복사"
# cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행"
nohup java -jar $JAR_FILE &
