#!/bin/bash
pwd
cd "$(dirname "$0")"
pwd
appname=@experiment.configuration.name@-@project.artifactId@-@project.version@
rm -rf $appname-electron
unzip $appname-electron.zip -d $appname-electron
cd $appname-electron


npm config set prefix '/srv/ExperimentTemplate/.npm-global'
PATH=/srv/ExperimentTemplate/.npm-global/bin:$PATH
npm install -g electron-forge
npm install --save
#electron-forge init $appname
#cd $appname
#electron-forge package --platform=win32 --arch=x64
electron-forge make all

mkdir /srv/target/electron
cp out/make/*linux*.zip ../@experiment.configuration.name@-linux.zip
cp out/make/*win32*.zip ../@experiment.configuration.name@-win32.zip
cp out/make/*darwin*.zip ../@experiment.configuration.name@-darwin.zip