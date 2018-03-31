-- set time zone
set @@global.time_zone = '+00:00';
-- create runelite:runelite user
create user 'runelite'@'localhost' identified by 'runelite';
-- create databases
create database runelite;
create database `runelite-cache2`;
create database `runelite-tracker`;
-- grant privileges to databases to our runelite user
grant all on runelite.* to 'runelite'@'localhost' identified by 'runelite';
grant all on `runelite-cache2`.* to 'runelite'@'localhost' identified by 'runelite';
grant all on `runelite-tracker`.* to 'runelite'@'localhost' identified by 'runelite';
flush privileges;
