CREATE TABLE public.users (
	id bigserial  PRIMARY KEY,
	login varchar(40) UNIQUE NOT NULL,
	password char(64) NOT NULL,
	name varchar(30),
	surname varchar(100)
);

CREATE TABLE public.notes (
	id bigserial PRIMARY KEY,
	title varchar(100),
	content varchar(1000)
);

CREATE TABLE public.user_notes (
	id bigserial PRIMARY KEY,
	user_id bigint REFERENCES users(id),
	note_id bigint REFERENCES notes(id),
	UNIQUE(user_id, note_id)
);

