
DROP TABLE IF EXISTS truck;
CREATE TABLE truck (
  truckId INT NOT NULL AUTO_INCREMENT,
  truckCode VARCHAR(255) NOT NULL UNIQUE,
  purchasedDate timestamp  NOT NULL,
  descriptions VARCHAR(255) NOT NULL,
  PRIMARY KEY (truckId)
);

DROP TABLE IF EXISTS orderz;
CREATE TABLE orderz (
  orderId INT NOT NULL AUTO_INCREMENT,
  petrolQty DOUBLE NOT NULL,
  orderDate timestamp NOT NULL,
  truckId INT,
  PRIMARY KEY (orderId),
  FOREIGN KEY (truckId) REFERENCES truck(truckId)
  ON UPDATE CASCADE
  ON DELETE CASCADE
);