#!/bin/sh

tar zxvf ../gz/apache-tomcat-8.5.37.tar.gz -C ../temp/ 

sudo mv ../temp/apache-tomcat-8.5.37 /usr/

sudo chmod 755 -R /usr/apache-tomcat-8.5.37/

sudo echo "export TOMCAT_HOME=/usr/apache-tomcat-8.5.37" >> ~/.bashrc
sudo echo "export PATH=\$TOMCAT_HOME/bin:\$PATH" >> ~/.bashrc
