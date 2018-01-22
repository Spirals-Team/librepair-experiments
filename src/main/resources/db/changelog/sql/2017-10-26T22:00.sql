-- This script insert user and client credentials for oauth2 authentication

--user_x_role table
insert into security.user_x_role (user_id, role) 
select user_id, 'ADMIN' as role from security.user where username = 'admin';

--oauth_client_details table
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('barClientIdPassword', 'secret', 'bar,read,write',
	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);