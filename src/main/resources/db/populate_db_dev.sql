DELETE FROM purchase;

INSERT INTO purchase(money, created_date, category, comment) VALUES(100, CURRENT TIMESTAMP, 'meal', 'bread and tea');
INSERT INTO purchase(money, created_date, category, comment) VALUES(100, '2018-04-28 13:45:59', 'meal', 'milk');