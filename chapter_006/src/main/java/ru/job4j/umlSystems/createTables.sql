CREATE TABLE IF NOT EXISTS states(
  id serial primary key,
  state character varying(256)
);

CREATE TABLE IF NOT EXISTS categories(
  id serial primary key,
  category character varying(256)
);

CREATE TABLE IF NOT EXISTS attachments(
  id serial primary key,
  attachment character varying(256)
);

CREATE TABLE IF NOT EXISTS commentaries(
  id serial primary key,
  commentary character varying(256)
);

CREATE TABLE IF NOT EXISTS items(
  id serial primary key,
  commentaries INT REFERENCES commentaries(id),
  attachments INT REFERENCES attachments(id),
  category INT REFERENCES categories(id),
  state INT REFERENCES states(id),
  username VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS rules(
  id serial primary key,
  rule VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS roles(
  id serial primary key,
  role character varying(256),
  rule int REFERENCES rules(id)
);

CREATE TABLE IF NOT EXISTS users(
  id serial primary key,
  name VARCHAR(256),
  item int REFERENCES items(id),
  role int REFERENCES roles(id)
);