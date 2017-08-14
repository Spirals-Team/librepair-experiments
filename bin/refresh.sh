#!/usr/bin/env bash
bin/pkgs.sh
bin/license-update.sh

git reset
git add rapidoid-commons/src/main/resources/rapidoid-classes.txt
git commit -m "Updated list of classes."
git log -n 1
