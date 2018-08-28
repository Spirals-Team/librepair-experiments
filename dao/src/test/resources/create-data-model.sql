CREATE TABLE IF NOT EXISTS employee (
  id            INT         NOT NULL AUTO_INCREMENT,
  first_name    VARCHAR(20),
  second_name   VARCHAR(20),
  last_name     VARCHAR(20) NOT NULL,
  birthdate     DATE,
  email         VARCHAR(40),
  icq           VARCHAR(20),
  skype         VARCHAR(30),
  note          VARCHAR(200),
  work_address  VARCHAR(200),
  home_address  VARCHAR(200),
  manager_id    INT,
  department_id INT,
  photo         BLOB        NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS department (
  id         INT         NOT NULL AUTO_INCREMENT,
  name       VARCHAR(50) NOT NULL,
  manager_id INT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS phone (
  id     INT         NOT NULL AUTO_INCREMENT,
  number VARCHAR(25) NOT NULL,
  type   INT         NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS employee_to_phone (
  id          INT NOT NULL AUTO_INCREMENT,
  phone_id    INT NOT NULL,
  employee_id INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user (
  id       INT         NOT NULL AUTO_INCREMENT,
  login    VARCHAR(30) NOT NULL,
  password VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE employee ADD FOREIGN KEY (manager_id) REFERENCES employee (id);

ALTER TABLE employee ADD FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE department ADD FOREIGN KEY (manager_id) REFERENCES employee (id);

ALTER TABLE employee_to_phone ADD FOREIGN KEY (phone_id) REFERENCES phone (id);

ALTER TABLE employee_to_phone ADD FOREIGN KEY (employee_id) REFERENCES employee (id);
