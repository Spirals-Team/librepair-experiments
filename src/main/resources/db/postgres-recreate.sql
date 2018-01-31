drop table if exists meal;
drop table if exists "user";
drop sequence if exists sequencer;

create sequence sequencer;

create table "user" (
  id int primary key default nextval('sequencer'),
  name varchar(128),
  email varchar(64) not null unique,
  password bytea not null,
  admin boolean not null default false,
  enabled boolean not null default true,
  calories_per_day_limit int not null check(calories_per_day_limit >= 0),
  registered_at timestamp with time zone not null default current_timestamp
);

create table meal (
  id int primary key default nextval('sequencer'),
  user_id int not null references "user"(id),
  "when" timestamp not null,
  "desc" varchar(256) not null,
  calories int not null check(calories >= 0),
  unique(user_id, "when")
);
