#!/usr/bin/env bash

openssl aes-256-cbc -K $encrypted_d16f88065f17_key -iv $encrypted_d16f88065f17_iv -in codesigning.asc.enc -out codesigning.asc -d
gpg --fast-import codesigning.asc
