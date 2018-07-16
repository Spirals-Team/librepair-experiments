insert into classe ( nom ) values ('d11');
insert into classe ( nom ) values ('d12');
insert into classe ( nom ) values ('d13');
insert into classe ( nom ) values ('d14');

insert into stagiaire(nom, prenom, email, photo_url, id_classe)
  values ('clopin', 'brigitte', 'bribri@hotmail.fr', 'https://www.valeursactuelles.com/sites/default/files/styles/image_article/public/2018-01/brigitte%20macron%20sipa.jpg?itok=0g8jrpff', 1);

insert into stagiaire(nom, prenom, email, photo_url, id_classe)
  values ('lupin', 'patrick', 'lp@gmail.com', 'https://odgers-prod-neu-cdn-tm-endpoint.azureedge.net/media/1351/patrik-kvikant.jpg',1 );
  
insert into stagiaire(nom, prenom, email, photo_url, id_classe)
  values ('lupin', 'patrick', 'lp@gmail.com', 'https://odgers-prod-neu-cdn-tm-endpoint.azureedge.net/media/1351/patrik-kvikant.jpg',2 );
  
insert into quizz(titre) values ('les animeaux de la forêt');

insert into stagiaire(nom, prenom, email, photo_url, id_classe)
  values ('lupin', 'patrick', 'lp@gmail.com', 'https://odgers-prod-neu-cdn-tm-endpoint.azureedge.net/media/1351/patrik-kvikant.jpg',4 );

insert into stagiaire(nom, prenom, email, photo_url, id_classe) values ('abitbol', 'georges', 'mondedemerde@clas.se', 'abitbol.jpg', 2);
insert into stagiaire(nom, prenom, email, photo_url, id_classe) values ('porc', 'francis', 'dudeleesport@afes.fr', 'porc.jpg',3);
insert into stagiaire(nom, prenom, email, photo_url, id_classe) values ('mansoif', 'gérard', 'gerard.mansoif@wanadoo.fr', 'mansoif.jpg',4);

insert into examen(titre,id_quizz,id_classe) values ('quiz d12 animaux de la forêts', 1, 1) ;

insert into examen(titre,id_quizz,id_classe) values ('quiz d12 animeaux de la forêts', 1, 1) ;

insert into note(note_sur_20, id_stagiaire, id_examen) values (12, 3, 1);
insert into note(note_sur_20, id_stagiaire, id_examen) values (15, 4, 1);
 
insert into classe(nom) values('d12');
insert into classe(nom) values('d13');
insert into classe(nom) values('d14');

insert into quizz(titre) values ('les animaux de la forêt');
insert into quizz(titre) values ('géographie');
insert into quizz(titre) values ('mathématiques');
insert into question(titre) values ('qui est clovis?');
insert into question(titre) values ('quel animage vole le plus vite?');
insert into compo_quizz(idquizz, idquestion) values (1, 1);
insert into compo_quizz(idquizz, idquestion) values (1, 2);
insert into compo_quizz(idquizz, idquestion) values (2, 1);  
insert into compo_quizz(idquizz, idquestion) values (2, 2);

insert into note(note_sur_20, id_stagiaire, id_examen) values (12, 3, 1);
insert into note(note_sur_20, id_stagiaire, id_examen) values (15, 4, 1);

insert into option_sondage(libelle, description) values ('libelle sondage 1','description du sondage numéro 1');
insert into option_sondage(libelle, description) values ('libelle sondage 2','description du sondage numéro 2');
insert into option_sondage(libelle, description) values ('libelle sondage 3','description du sondage numéro 3');
insert into option_sondage(libelle, description) values ('libelle sondage 4','description du sondage numéro 4');
insert into option_sondage(libelle, description) values ('libelle sondage 5','description du sondage numéro 5');
insert into option_sondage(libelle, description) values ('libelle sondage 6','description du sondage numéro 6');
insert into option_sondage(libelle, description) values ('libelle sondage 7','description du sondage numéro 7');

insert into sondage(classe_id) values (1);
insert into sondage(classe_id) values (2);
insert into sondage(classe_id) values (3);
insert into sondage(classe_id) values (1);

insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (1,3);
insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (1,5);
insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (1,7);

insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (2,1);
insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (2,4);
insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (2,7);

insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (3,2);
insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (3,6);

insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (4,1);
insert into sondage_option_sondage ( id_sondage, id_option_sondage ) values (4,7);

insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (1, 2, 1);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (1, 2, 2);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (1, 2, 3);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (1, 3, 1);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (1, 3, 2);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (1, 3, 3);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (2, 3, 1);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (2, 3, 2);
insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (2, 3, 3);