#!/bin/bash
pwd
cd "$(dirname "$0")"
pwd
appname=@experiment.configuration.name@-@project.artifactId@-@project.version@
rm -rf $appname-electron
unzip $appname-electron.zip -d $appname-electron
cd $appname-electron




npm install -g electron-forge
npm install --save
#electron-forge init $appname
#cd $appname
#electron-forge package --platform=win32 --arch=x64
electron-forge make all
cp out/make/*darwin*.zip $appname.zip ../@experiment.configuration.name@-darwin.zip