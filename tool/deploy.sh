#!/bin/sh
if [[ $# == 1 ]]
then
  runEnv=$1
else
  runEnv=local
fi

mvn clean package -Dmaven.test.skip=true
nohup java -jar -Dspring.profiles.active=$runEnv target/common-web.jar > /dev/null 2>&1 &