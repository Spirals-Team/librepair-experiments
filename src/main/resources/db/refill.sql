delete from meal;
delete from "user";
alter sequence sequencer restart 1;

insert into "user" (id, name, email, password, admin, calories_per_day_limit) values
  (1, 'Admin', 'admin@example.com', decode(md5('admin'), 'hex'), true, 2000),
  (2, 'User', 'user@example.com', decode(md5('user'), 'hex'), false, 3000);

insert into meal (user_id, "when", "desc", calories) values
  -- Admin
  (1, '2017-12-10 10:00', 'Завтрак', 500),
  (1, '2017-12-10 15:00', 'Обед', 800),
  (1, '2017-12-10 20:00', 'Ужин', 1000),
  (1, '2017-12-11 10:00', 'Завтрак', 400),
  (1, '2017-12-11 15:00', 'Обед', 700),
  (1, '2017-12-11 20:00', 'Ужин', 900),
  -- User
  (2, '2017-12-10 10:00', 'Завтрак', 800),
  (2, '2017-12-10 15:00', 'Обед', 1500),
  (2, '2017-12-10 20:00', 'Ужин', 1200),
  (2, '2017-12-11 10:00', 'Завтрак', 800),
  (2, '2017-12-11 15:00', 'Обед', 1200),
  (2, '2017-12-11 20:00', 'Ужин', 1000);
