ALTER TABLE student ADD email varchar(255) DEFAULT NULL;
ALTER TABLE student ADD phone varchar(255) DEFAULT NULL;
ALTER TABLE student ADD resume_url varchar(255) DEFAULT NULL;
ALTER TABLE student ADD looking_for_work  BOOLEAN DEFAULT TRUE;
ALTER TABLE student ADD feedback LONGTEXT DEFAULT NULL;
