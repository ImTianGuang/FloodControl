#!/bin/sh
if [[ $# == 1 ]]
then
  runEnv=$1
else
  runEnv=local
fi
mvn clean package -Dmaven.test.skip=true
echo "kill the process..."
ps aux | grep common-web.jar | awk 'NR==1{print $2}'|xargs kill -9
echo "nohup java -jar -Dspring.profiles.active=$runEnv target/common-web.jar > /dev/null 2>&1 &"
nohup java -jar -Dspring.profiles.active=$runEnv target/common-web.jar > /dev/null 2>&1 &
ps aux | grep common-web.jar
