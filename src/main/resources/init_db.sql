-- DROP TABLE auditorium_seats;
-- DROP TABLE event_auditoriums;
-- DROP TABLE user_lucky_dates;
--
-- DROP TABLE ticket;
-- DROP TABLE users;
-- DROP TABLE event;
-- DROP TABLE auditorium;


CREATE TABLE users (
  id         BIGINT                                 NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 0, INCREMENT BY 1),
  first_name VARCHAR(50)                            NOT NULL,
  last_name  VARCHAR(50)                            NOT NULL,
  email      VARCHAR(50)                            NOT NULL,
  birthday   TIMESTAMP                              NOT NULL,
  password   VARCHAR(60)                            NOT NULL,
  roles      VARCHAR(200) DEFAULT 'REGISTERED_USER' NOT NULL,
  CONSTRAINT primary_key_users PRIMARY KEY (id),
  --1st approach to make column unique
  --UNIQUE(email)
  --2n approach to make column unique
  CONSTRAINT users_email_unique UNIQUE (email)
);

CREATE TABLE user_accounts (
  id      BIGINT           NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 0, INCREMENT BY 1),
  user_id BIGINT           NOT NULL,
  name    VARCHAR(50)      NOT NULL,
  money   DOUBLE PRECISION NOT NULL,
  CONSTRAINT primary_key_user_accounts PRIMARY KEY (id),
  CONSTRAINT user_accounts_name_unique UNIQUE (name),
  CONSTRAINT user_accounts_userid_ref FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);

CREATE TABLE event (
  id         BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 0, INCREMENT BY 1),
  name       VARCHAR(50) NOT NULL,
  base_price DOUBLE PRECISION,
  rating     VARCHAR(50) NOT NULL,
  CONSTRAINT primary_key_event PRIMARY KEY (id)
);

CREATE TABLE ticket (
  id        BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 0, INCREMENT BY 1),
  --1st approach to create foreign key
  --   user_id   BIGINT REFERENCES users (id),
  user_id   BIGINT NOT NULL,
  event_id  BIGINT NOT NULL,
  date_time TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
  seat      BIGINT NOT NULL,
  CONSTRAINT primary_key_ticket PRIMARY KEY (id),
  --2nd approach to create foreign key with named constraint
  CONSTRAINT ticket_userid_ref FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE,
  CONSTRAINT ticket_eventid_ref FOREIGN KEY (event_id) REFERENCES event (id)
    ON DELETE CASCADE
);

CREATE TABLE auditorium (
  name            VARCHAR(50) NOT NULL PRIMARY KEY,
  number_of_seats BIGINT      NOT NULL
);

--Wiring tables
CREATE TABLE user_lucky_dates (
  user_id        BIGINT    NOT NULL,
  lucky_datetime TIMESTAMP NOT NULL,
  CONSTRAINT primary_key_lucky PRIMARY KEY (user_id, lucky_datetime),
  CONSTRAINT user_lucky_dates_userid_ref FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);
CREATE TABLE event_auditoriums (
  event_id        BIGINT      NOT NULL REFERENCES event (id),
  auditorium_name VARCHAR(50) NOT NULL REFERENCES auditorium (name),
  air_date        TIMESTAMP   NOT NULL,
  CONSTRAINT primary_key_ea PRIMARY KEY (event_id, auditorium_name, air_date)
);

CREATE TABLE auditorium_seats (
  auditorium_name VARCHAR(50) NOT NULL REFERENCES auditorium (name),
  vip_seat        BIGINT      NOT NULL,
  CONSTRAINT primary_key_as PRIMARY KEY (auditorium_name, vip_seat)
);

--Aspect tables
CREATE TABLE event_counter_audit (
  event_name VARCHAR(50) NOT NULL,
  name       VARCHAR(50) NOT NULL,
  count      INTEGER     NOT NULL,
  CONSTRAINT primary_key_audit_counter PRIMARY KEY (event_name, name)
);

CREATE TABLE user_discount_audit (
  user_id       BIGINT      NOT NULL,
  discount_name VARCHAR(50) NOT NULL,
  count         INTEGER     NOT NULL,
  CONSTRAINT primary_key_user_discount_audit PRIMARY KEY (user_id, discount_name),
  CONSTRAINT user_discount_audit_userid_ref FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);

-- for Remember-Me
CREATE TABLE persistent_logins (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL
);