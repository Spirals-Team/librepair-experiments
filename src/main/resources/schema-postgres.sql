drop table if exists sondage_option_sondage;
drop table if exists question_compo;
drop table if exists sondage;
drop table if exists option_sondage;
drop table if exists note;
drop table if exists examen;
drop table if exists compo_quizz;
drop table if exists duel;
drop table if exists quizz;
drop table if exists option_question;
drop table if exists question;
drop table if exists stagiaire;
drop table if exists classe;

create table classe (
  id serial primary key,
  nom varchar(75) not null
);

create table stagiaire (
  id serial primary key,
  nom varchar(75) not null,
  prenom varchar(75) not null,
  email varchar(75) not null,
  photo_url varchar(200) not null,
  id_classe bigint not null,
  foreign key (id_classe) references classe(id)
);

create table question (
  id serial primary key,
  titre varchar(75)
);

create table option_question (
  id serial primary key,
  libelle varchar(75) not null,
  ok boolean not null
);

create table question_compo (
  id serial primary key,
  id_opt bigint not null,
  id_que bigint not null,
  foreign key (id_opt) references option_question(id),
  foreign key (id_que) references question(id)
);

create table quizz(
	id serial primary key,
	titre varchar(255) not null
);

create table compo_quizz (
	id serial primary key,
	idquizz bigint,
	idquestion bigint,
	foreign key (idquizz) references quizz(id),
	foreign key (idquestion) references question(id)
);

create table examen(
	id serial primary key,
	titre varchar(75) not null,
	id_quizz bigint not null,
	id_classe bigint not null,
	foreign key (id_quizz) references quizz(id),
	foreign key (id_classe) references classe(id)
);


create table note (
  id serial primary key,
  note_sur_20 decimal(4,2) not null,
  id_stagiaire bigint not null,
  id_examen bigint not null,
  foreign key (id_stagiaire) references stagiaire(id),
  foreign key (id_examen) references examen(id)
);

create table option_sondage (
  id serial primary key,
  libelle varchar(75) not null,
  description varchar(150) not null
);

create table sondage (
  id serial primary key,
  classe_id bigint default null,
  foreign key (classe_id) references classe (id)
);

create table sondage_option_sondage (
  id_sondage bigint default null,
  id_option_sondage bigint default null,
  foreign key (id_option_sondage) references option_sondage (id),
  foreign key (id_sondage) references sondage (id)
);

create table duel (
	id serial primary key,
	stagiairea_id bigint not null,
	stagiaireb_id bigint not null,
	quizz_id bigint not null,
	foreign key (stagiairea_id) references stagiaire(id),
	foreign key (stagiaireb_id) references stagiaire(id),
	foreign key (quizz_id) references quizz(id)
);

