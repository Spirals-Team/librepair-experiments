create user semillero@'%' identified by 'semillero';

create database semillero;

grant all privileges on semillero.* to semillero@'%';
