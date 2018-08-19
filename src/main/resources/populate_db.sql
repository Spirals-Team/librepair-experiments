DELETE FROM event_counter_audit;
DELETE FROM user_discount_audit;
--
DELETE FROM auditorium_seats;
DELETE FROM event_auditoriums;
DELETE FROM user_lucky_dates;
--
DELETE FROM ticket;
DELETE FROM user_accounts;
DELETE FROM users;
DELETE FROM event;
DELETE FROM auditorium;
--

INSERT INTO auditorium (name, number_of_seats) VALUES ('alpha', 10);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('alpha', 1);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('alpha', 2);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('alpha', 3);

INSERT INTO auditorium (name, number_of_seats) VALUES ('beta', 9);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('beta', 5);

INSERT INTO auditorium (name, number_of_seats) VALUES ('gama', 15);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('gama', 1);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('gama', 10);
INSERT INTO auditorium_seats (auditorium_name, vip_seat) VALUES ('gama', 15);


