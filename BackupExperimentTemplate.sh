#!/bin/bash
# this script produces a minimal zip of the working directory with the required stimuli files but not the large original stimuli files, for convenience only
cd "$(dirname "$0")";
mvn clean;
zip -r ../ExperimentTemplate-common-module-$(date +%F).zip . -x "*.DS_Store" "target" "*.wav" "*.mov" "*.mpeg" "*.sql.gz" "out" "node_modules" "*.zip" "*.tar" "*.dmg" "*.mp4" "*.ogg" "*.mp3" "Electron*" "*.jpg" "*.aiff"
