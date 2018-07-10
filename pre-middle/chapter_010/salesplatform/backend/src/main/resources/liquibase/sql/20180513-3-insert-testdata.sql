-- insert cars
INSERT INTO cars (body_id, category_id, fuel_id, model_id, transmission_id, engine_size, mileage, year)
VALUES (2, 2, 2, 2, 2, 1.2, 78000, 2008);

INSERT INTO cars (body_id, category_id, fuel_id, model_id, transmission_id, engine_size, mileage, year)
VALUES (3, 2, 4, 3, 2, 2.2, 78000, 2015);

-- insert users
INSERT INTO users (email, passw, role_id, phone) VALUES (
    'ivanov@email.com',
    '1234',
    '1',
    '555-55-55'
);

-- insert posts
INSERT INTO posts (content, price, is_active, car_id, user_id)
VALUES ('Electric windows, Air conditioning, Satellite navigation, Parking aid, MP3 player, CD player, Bluetooth, Height adjustable driver''s seat, Height adjustable passenger seat, Folding rear seats, Alloy wheels, Spare wheel (Full), Power steering', 2000.0, TRUE, 1, 1);

INSERT INTO posts (content, price, is_active, car_id, user_id)
VALUES ('Electric windows, Air conditioning, Satellite navigation, Parking aid, MP3 player, CD player, Bluetooth, Height adjustable driver''s seat, Height adjustable passenger seat, Folding rear seats, Alloy wheels, Spare wheel (Full), Power steering', 25000.0, TRUE, 2, 1);
