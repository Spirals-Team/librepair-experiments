insert into states(state) values ('one');

insert into categories(category) values ('one');

insert into attachments(attachment) values ('one');

insert into commentaries(commentary) values ('one');

insert into items(commentaries, attachments, category, state, username) values (1, 1, 1, 1, 'first');

insert into rules(rule) values ('one');

insert into roles(role, rule) values ('one', 1);

insert into users(name, item, role) values ('FirstUser' ,1, 1);