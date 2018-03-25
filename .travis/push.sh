#!/bin/sh

setup_git() {
  mvn clean
  git status
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
}

commit_website_files() {
  git checkout -b gh-pages
  git add .
  git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
  git remote add origin-pages https://${GH_TOKEN}@github.com/shortbytes/dayzero.git > /dev/null 2>&1
  git push --quiet --set-upstream origin-pages gh-pages
  git merge master  
}
setup_git
commit_website_files
upload_files