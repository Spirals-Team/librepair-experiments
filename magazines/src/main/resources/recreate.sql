drop table if exists author;
drop table if exists article;
drop table if exists magazine;
drop table if exists publisher;

create table publisher (
  publisher_id serial primary key,
  name varchar(64) not null unique
);

create table magazine (
  magazine_id serial primary key,
  publisher_id int not null references publisher(publisher_id),
  name varchar(32) not null unique
);

create table article (
  article_id serial primary key,
  magazine_id int not null references magazine(magazine_id),
  name text not null
);

create table author (
  author_id serial primary key,
  article_id int not null unique references article(article_id),
  name varchar(64) not null
);
