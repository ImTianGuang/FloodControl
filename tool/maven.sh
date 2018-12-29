#!/bin/sh
tar zxvf ../gz/apache-maven-3.5.4-bin.tar.gz -C ../temp/

sudo mv ../temp/apache-maven-3.5.4/ /opt/apache-maven-3.5.4/

sudo ln -s /opt/apache-maven-3.5.4/ /opt/maven

echo "export M2_HOME=/opt/maven" >> ~/.bashrc
echo "export M2=\$M2_HOME/bin" >> ~/.bashrc
echo "export PATH=\$M2:\$PATH" >> ~/.bashrc
