#!/usr/bin/env bash
rm -rf keystore.jceks
keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg HMacSHA256 -keysize 2048 -alias HS256 -keypass zeroup
keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg HMacSHA384 -keysize 2048 -alias HS384 -keypass zeroup
keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg HMacSHA512 -keysize 2048 -alias HS512 -keypass zeroup
keytool -genkey -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg RSA -keysize 2048 -alias RS256 -keypass zeroup -sigalg SHA256withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkey -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg RSA -keysize 2048 -alias RS384 -keypass zeroup -sigalg SHA384withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkey -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg RSA -keysize 2048 -alias RS512 -keypass zeroup -sigalg SHA512withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkeypair -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg EC -keysize 256 -alias ES256 -keypass zeroup -sigalg SHA256withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkeypair -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg EC -keysize 384 -alias ES384 -keypass zeroup -sigalg SHA384withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkeypair -keystore keystore.jceks -storetype jceks -storepass zeroup -keyalg EC -keysize 521 -alias ES512 -keypass zeroup -sigalg SHA512withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360