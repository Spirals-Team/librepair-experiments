ALTER TABLE security.user ALTER COLUMN pass TYPE VARCHAR(256);

UPDATE security.user SET pass = '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri' WHERE username = 'admin';