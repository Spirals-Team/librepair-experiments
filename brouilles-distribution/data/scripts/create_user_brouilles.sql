-- DROP 

DROP TABLESPACE brouilles_tablespace INCLUDING CONTENTS AND DATAFILES CASCADE CONSTRAINTS;
DROP TABLESPACE brouilles_tablespace_temp INCLUDING CONTENTS AND DATAFILES CASCADE CONSTRAINTS;
drop user brouilles cascade;


-- CREATION

-- Tablespaces
create tablespace brouilles_tablespace datafile 'user_brouilles.dbf' size 200M AUTOEXTEND ON NEXT 50M;
create temporary tablespace brouilles_tablespace_temp tempfile 'user_brouilles_temp.dbf' size 20M autoextend on;

-- User
create user brouilles identified by brouilles default tablespace brouilles_tablespace temporary tablespace brouilles_tablespace_temp;

-- Grant
GRANT CREATE SESSION TO brouilles;

grant create table to brouilles;

grant unlimited tablespace to brouilles;

GRANT CONNECT TO brouilles;

grant all privileges to brouilles;



