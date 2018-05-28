#!/bin/bash
pwd
cd "$(dirname "$0")"
pwd
appname=ld_screensize-frinex-gui-0.1-testing-SNAPSHOT
rm -rf $appname-electron
unzip $appname-electron.zip -d $appname-electron
cd $appname-electron




npm install -g electron-forge
electron-forge init $appname
cd $appname
electron-forge make