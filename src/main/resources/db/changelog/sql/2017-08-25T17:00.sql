INSERT INTO security."user"(
	user_id, username, pass, active)
	VALUES (nextval('security.user_user_id_seq'), 'admin', 'admin', true);