#!/bin/sh
SERVICE_NAME=school
PATH_TO_JAR=/opt/$SERVICE_NAME.jar
PID_PATH_NAME=/tmp/$SERVICE_NAME-pid
LOG_PATH=/opt/$SERVICE_NAME.out
case $1 in
start)
       echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then
       nohup java -jar -Dspring.profiles.active=dev $PATH_TO_JAR > $LOG_PATH 2> /dev/null &
                   echo $! > $PID_PATH_NAME
       echo "$SERVICE_NAME started ..."
  else
       echo "$SERVICE_NAME is already running ..."
  fi
;;
stop)
  if [ -f $PID_PATH_NAME ]; then
         PID=$(cat $PID_PATH_NAME);
         echo "$SERVICE_NAME stopping ..."
         kill $PID;
         echo "$SERVICE_NAME stopped ..."
         rm $PID_PATH_NAME
  else
         echo "$SERVICE_NAME is not running ..."
  fi
;;
restart)
  if [ -f $PID_PATH_NAME ]; then
      PID=$(cat $PID_PATH_NAME);
      echo "$SERVICE_NAME stopping ...";
      kill $PID;
      echo "$SERVICE_NAME stopped ...";
      rm $PID_PATH_NAME
      echo "$SERVICE_NAME starting ..."
      nohup java -jar -Dspring.profiles.active=dev $PATH_TO_JAR > $LOG_PATH 2>  /dev/null &
      echo $! > $PID_PATH_NAME
      echo "$SERVICE_NAME started ..."
  else
      echo "$SERVICE_NAME is not running ..."
     fi     ;;
 esac