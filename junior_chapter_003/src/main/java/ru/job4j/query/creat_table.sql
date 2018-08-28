CREATE TABLE type (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(20)
);
CREATE TABLE product (
  id           SERIAL PRIMARY KEY,
  name         VARCHAR(20),
  type_id      INTEGER REFERENCES type (id),
  expired_date TIMESTAMP,
  price        INTEGER
);