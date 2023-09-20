#!/usr/bin/env bash

APP_NAME="dev-0.0.1-SNAPSHOT.jar"
PROJECT_ROOT="/home/ubuntu/app/build/libs/"
JAR_FILE="$PROJECT_ROOT dev-0.0.1-SNAPSHOT.jar"

#APP_LOG="$PROJECT_ROOT/application.log"
#ERROR_LOG="$PROJECT_ROOT/error.log"
#DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)


CURRENT_PID=$(pgrep -f $APP_NAME)

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]; then
  echo " 현재 실행중인 애플리케이션이 없습니다"
else
#  echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료 " >> $DEPLOY_LOG
    sudo kill -15 $CURRENT_PID
    sleep 5
fi


# build 파일 복사
#echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
#cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
#echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar /home/ubuntu/app/build/libs/dev-0.0.1-SNAPSHOT.jar &

#CURRENT_PID=$(pgrep -f $JAR_FILE)
#echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG