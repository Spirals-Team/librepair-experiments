-- user configuration
CREATE USER auth_token_user WITH PASSWORD 'auth_token_user_password';
GRANT CONNECT ON DATABASE auth_token_database TO auth_token_user;

-- connect to created database
\connect auth_token_database

-----------------------------------
---			TOKEN STORE			---
-----------------------------------
CREATE TABLE oauth_access_token (
	token_id character varying(256) NOT NULL,
	token bytea NOT NULL,
	authentication_id character varying(256) NOT NULL,
	user_name character varying(256) NOT NULL,
	client_id character varying(256) NOT NULL,
	authentication bytea NOT NULL,
	refresh_token character varying(256) NOT NULL
);

ALTER TABLE ONLY oauth_access_token
    ADD CONSTRAINT oauth_access_token_pkey PRIMARY KEY (token_id);

GRANT SELECT, INSERT, UPDATE, DELETE
	ON oauth_access_token TO auth_token_user;

-----------------------------------
---		  REFRESH TOKENS		---
-----------------------------------
CREATE TABLE oauth_refresh_token (
	token_id character varying(256) NOT NULL,
	token bytea NOT NULL,
	authentication bytea NOT NULL
);

ALTER TABLE ONLY oauth_refresh_token
    ADD CONSTRAINT oauth_refresh_token_pkey PRIMARY KEY (token_id);

GRANT SELECT, INSERT, UPDATE, DELETE
ON oauth_refresh_token TO auth_token_user;