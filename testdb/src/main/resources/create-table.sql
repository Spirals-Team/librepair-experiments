DROP TABLE IF EXISTS teams;
CREATE TABLE teams (

  team_id       INT NOT NULL AUTO_INCREMENT,
  team_name     VARCHAR(45) NOT NULL,
  team_country  VARCHAR(45) NOT NULL,
  PRIMARY KEY (team_id)
);

DROP TABLE IF EXISTS players;
CREATE TABLE players (

  player_id INT NOT NULL AUTO_INCREMENT,
  player_name VARCHAR(45) NOT NULL,
  player_number INT NOT NULL,
  player_age INT NOT NULL,
  player_cost INT NOT NULL,
  player_team_id INT,
  PRIMARY KEY (player_id),
  FOREIGN KEY (player_team_id) REFERENCES teams (team_id)
);

DROP TABLE IF EXISTS meetings;
CREATE TABLE meetings (

  meeting_id INT NOT NULL AUTO_INCREMENT,
  first_team INT,
  second_team INT,
  meeting_date DATE NOT NULL,
  PRIMARY KEY (meeting_id),
  FOREIGN KEY (first_team) REFERENCES teams (team_id),
  FOREIGN KEY (second_team) REFERENCES teams (team_id)
);