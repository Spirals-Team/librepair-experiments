drop table if exists meal;
drop table if exists user;

create table user (
  user_id int identity,
  name varchar(128),
  email varchar(64) not null unique,
  password bytea(16) not null,
  admin boolean not null default false,
  enabled boolean not null default true,
  calories_per_day_limit int not null check(calories_per_day_limit >= 0),
  registered_at timestamp with time zone not null default current_timestamp
);
alter sequence user_user_id_seq restart 100;

create table meal (
  meal_id int identity,
  user_id int not null references user(user_id),
  when timestamp not null,
  desc varchar(256) not null,
  calories int not null check(calories >= 0),
  unique(user_id, when)
);
