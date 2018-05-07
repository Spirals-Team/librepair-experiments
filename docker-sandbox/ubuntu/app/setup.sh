#!/bin/bash

# Install softwares
apt-get update -y
apt-get -y -o APT::Immediate-Configure=false install wget
wget -O- http://archive.apache.org/dist/bigtop/bigtop-1.2.1/repos/GPG-KEY-bigtop | apt-key add -
wget -O /etc/apt/sources.list.d/bigtop-1.2.1.list http://archive.apache.org/dist/bigtop/bigtop-1.2.1/repos/ubuntu16.04/bigtop.list

apt-get install -y -q --no-install-recommends openjdk-8-jre-headless vim screen curl sudo unzip man openssh-server unzip hadoop\*

# Download atrato apex cli package temporarily to build the docker image. Later this should be replaced with 
# the one built from dist folder. Captured in APEXCORE-813.
wget https://github.com/atrato/apex-cli-package/releases/download/v3.7.0/apex-cli-package-3.7.0-bin.zip
unzip apex-cli-package-3.7.0-bin.zip
pushd apex-cli-package-3.7.0
mkdir -p /usr/lib/apex
mv * /usr/lib/apex 
popd
rm -rf apex-cli-package-3.7.0
rm apex-cli-package-3.7.0-bin.zip
ln -s /usr/lib/apex/bin/apex /usr/bin/apex

# Autodetect JAVA_HOME if not defined
. /usr/lib/bigtop-utils/bigtop-detect-javahome

## turn off YARN nodemanager vmem check
sed -i 's#</configuration>##' /etc/hadoop/conf/yarn-site.xml
cat >> /etc/hadoop/conf/yarn-site.xml << EOF
  <property>
    <name>yarn.nodemanager.vmem-check-enabled</name>
    <value>false</value>
  </property>
</configuration>
EOF

## enable WebHDFS and append
sed -i 's#</configuration>##' /etc/hadoop/conf/hdfs-site.xml
cat >> /etc/hadoop/conf/hdfs-site.xml << EOF
  <property>
    <name>dfs.webhdfs.enabled</name>
    <value>true</value>
  </property>
  <property>
    <name>dfs.support.append</name>
    <value>true</value>
  </property>
  <property>
    <name>dfs.support.broken.append</name>
    <value>true</value>
  </property>
  <property>
    <name>dfs.permissions.enabled</name>
    <value>false</value>
  </property>
</configuration>
EOF

## format NameNode
/etc/init.d/hadoop-hdfs-namenode init
## start HDFS
for i in hadoop-hdfs-namenode hadoop-hdfs-datanode ; do service $i start ; done
## initialize HDFS
/usr/lib/hadoop/libexec/init-hdfs.sh
## stop HDFS
for i in hadoop-hdfs-namenode hadoop-hdfs-datanode ; do service $i stop ; done
## clean up
apt-get autoclean
rm -rf /var/lib/apt/lists/*
rm -rf /var/log/hadoop-hdfs/*

# Creating user
echo 'root:sc@mb0t' |chpasswd
useradd apex -s /bin/bash -U -G sudo -p apex -m
echo "apex:apex" |chpasswd
echo 'apex ALL=(ALL) NOPASSWD: /etc/init.d/hadoop*' >> /etc/sudoers
echo 'apex ALL=(ALL) NOPASSWD: /etc/init.d/ssh*' >> /etc/sudoers
