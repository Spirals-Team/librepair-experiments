DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS e_mall_user;

CREATE TABLE oauth_client_details (
  client_id varchar(256) NOT NULL,
  resource_ids varchar(256) DEFAULT NULL,
  client_secret varchar(256) DEFAULT NULL,
  scope varchar(256) DEFAULT NULL,
  authorized_grant_types varchar(256) DEFAULT NULL,
  web_server_redirect_uri varchar(256) DEFAULT NULL,
  authorities varchar(256) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(256) DEFAULT NULL,
  PRIMARY KEY (client_id)
)

CREATE TABLE e_mall_user (
  id bigint(20) NOT NULL,
  username varchar(20) NOT NULL,
  password varchar(100) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (id)
)