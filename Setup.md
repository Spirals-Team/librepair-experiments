We currently use mysql to run the database locally, you'll probably need the following steps to do so successfully

To have launchd start mysql now and restart at login:
  brew services start mysql
Or, if you don't want/need a background service you can just run:
  mysql.server start
  
You can then do sudo mysql to run it

When you have mysql running, the first time, you should perform the below
mysql> create database sysc; -- Create the new database
mysql> create user ‘user’@‘localhost' identified by ‘password’; -- Creates the user
mysql> grant all on sysc.* to ‘user’@‘localhost'; -- Gives all the privileges to the new user on the newly created database
