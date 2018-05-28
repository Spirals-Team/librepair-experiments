Drop table if exists grade;

CREATE TABLE grade (
  Id INT(11) NOT NULL,
  code VARCHAR(255) DEFAULT NULL,
  nbHeuresBase DECIMAL(20, 2) DEFAULT NULL,
  tauxBase DECIMAL(20, 2) DEFAULT NULL,
  PRIMARY KEY (Id)
);