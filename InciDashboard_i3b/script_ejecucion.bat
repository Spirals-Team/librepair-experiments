@echo off
set folder="C:\tmp"
cd /d %folder%
for /F "delims=" %%i in ('dir /b') do (rmdir "%%i" /s/q || del "%%i" /s/q)
cd %~dp0
START bin\windows\zookeeper-server-start.bat config\zookeeper.properties
timeout 5
START bin\windows\kafka-server-start.bat config\server.properties
