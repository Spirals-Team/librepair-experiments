@ECHO OFF

@ECHO Stopping thingsboard ...
net stop thingsboard

@ECHO Uninstalling thingsboard ...
%~dp0thingsboard.exe uninstall

@ECHO DONE.