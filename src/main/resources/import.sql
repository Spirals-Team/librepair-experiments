-- Users
INSERT INTO user (id, first_name, last_name, email, username, password) VALUES (1, 'Andrzej', 'Duda', 'aduda@gmail.com', 'admin', 'password');
INSERT INTO user (id, first_name, last_name, email, username, password) VALUES (2, 'Rafal', 'Mitula', 'rmitula@gmail.com', 'rmitula', 'password');
INSERT INTO user (id, first_name, last_name, email, username, password) VALUES (3, 'Kamil', 'Kowalski', 'kamilkowalski@gmail.com', 'kamil', 'password');

-- Categories
INSERT INTO category (id, name) VALUES (1, 'shoes');
INSERT INTO category (id, name) VALUES (2, 't-shirts');

-- Category 1
INSERT INTO product (id, name, quanity_In_Stock, price, category_id) VALUES (1, 'Nike Air Max 1', 10, 450.99, 1);
INSERT INTO product (id, name, quanity_In_Stock, price, category_id) VALUES (2, 'Adidas Superstar', 10, 500.50, 1);
INSERT INTO product (id, name, quanity_In_Stock, price, category_id) VALUES (3, 'Converse All Star', 10, 222.22, 1);
INSERT INTO product (id, name, quanity_In_Stock, price, category_id) VALUES (4, 'Onitsuka Tiger Corsair', 350, 450.1, 1);

-- Category 2
INSERT INTO product (id, name, quanity_In_Stock, price, category_id) VALUES (5, 'Supereme Bogo Navy', 2, 500.1, 2);

-- Orders

INSERT INTO order_table(id, product_id, user_id, quantity, order_date, status) VALUES (1, 1, 1, 1, '2018-03-15 17:30:00', 'PLACED');
INSERT INTO order_table(id, product_id, user_id, quantity, order_date, status) VALUES (2, 1, 3, 3, '2018-03-05 14:20:10', 'APPROVED');
INSERT INTO order_table(id, product_id, user_id, quantity, order_date, status) VALUES (3, 3, 2, 1, '2018-07-17 08:30:00', 'PLACED');

