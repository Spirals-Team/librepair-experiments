-----------------------------------
---	  EMAIL CONFIRMATION TOKEN	---
-----------------------------------
CREATE TABLE email_confirmation_tokens (
   	id bigserial NOT NULL,
	created timestamp without time zone NOT NULL DEFAULT now()::timestamp,
    last_updated timestamp without time zone NOT NULL DEFAULT now()::timestamp,
    token_string character(36) NOT NULL,
	expiration_date timestamp without time zone NOT NULL,
	user_id BIGINT NOT NULL
);

ALTER TABLE ONLY email_confirmation_tokens
    ADD CONSTRAINT email_confirmation_tokens_pkey PRIMARY KEY (id);

ALTER TABLE ONLY email_confirmation_tokens
    ADD CONSTRAINT email_confirmation_tokens_user FOREIGN KEY (user_id) REFERENCES users(id);