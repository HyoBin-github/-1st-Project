##!/usr/bin/env bash
#APP_NAME="dev-0.0.1-SNAPSHOT.jar"
#PROJECT_ROOT="/home/ubuntu/app/build/libs"
#JAR_FILE="$PROJECT_ROOT/dev-0.0.1-SNAPSHOT.jar"
#
#APP_LOG="$PROJECT_ROOT/application.log"
#ERROR_LOG="$PROJECT_ROOT/error.log"
#DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
#
#TIME_NOW=$(date +%c)
#
#CURRENT_PID=$(pgrep -f $APP_NAME)
#
#if [ -z $CURRENT_PID ]; then
#  echo "현재 실행중인 애플리케이션이 없습니다"
#else
#  echo "실행중인애플리케이션 종료 "
#  sudo kill -15 $JAR_FILE
#fi
#
#
## jar 파일 실행
#echo "파일 실행"
#nohup java -jar $JAR_FILE

#!/usr/bin/env bash
APP_NAME="dev-0.0.1-SNAPSHOT.jar"
PROJECT_ROOT="/home/ubuntu/app/build/libs"
JAR_FILE="$PROJECT_ROOT/dev-0.0.1-SNAPSHOT.jar"

#APP_LOG="$PROJECT_ROOT/application.log"
#ERROR_LOG="$PROJECT_ROOT/error.log"
#DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]; then
  echo "현재 실행중인 애플리케이션이 없습니다"
else
  echo "실행중인애플리케이션 종료 "
  sudo kill -15 $JAR_FILE
fi


# build 파일 복사
#echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
#cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
#echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
echo "jar 빌드"
nohup java -jar $JAR_FILE

#CURRENT_PID=$(pgrep -f $JAR_FILE)