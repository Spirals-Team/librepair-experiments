INSERT INTO rola (id_roli, rola) VALUES (1, 'ADMIN');
INSERT INTO rola (id_roli, rola) VALUES (2, 'USER');


INSERT INTO uzytkownik (id_uzytkownika, email, haslo, imie, nazwisko, active) VALUES (1, 'user@user', '$2a$10$Anb7jpgtB7VNkiHyV0kvWuYgHsHwKRQMPzQ.EOZLGXgnj/8bSzfha', 'user', 'user', 1);
INSERT INTO uzytkownik (id_uzytkownika, email, haslo, imie, nazwisko, active) VALUES (2, 'usedr@usder', '$2a$10$Anb7jpgtB7VNkiHyV0kvWuYgHsHwKRQMPzQ.EOZLGXgnj/8bSzfha', 'user', 'user', 1);
INSERT INTO uzytkownik (id_uzytkownika, email, haslo, imie, nazwisko, active) VALUES (3, 'admin@admin', '$2a$10$Anb7jpgtB7VNkiHyV0kvWuYgHsHwKRQMPzQ.EOZLGXgnj/8bSzfha', 'admin', 'admin', 1);

INSERT INTO uzytkownik_rola (id_uzytkownika, id_roli) VALUES (1, 2);
INSERT INTO uzytkownik_rola (id_uzytkownika, id_roli) VALUES (2, 2);
INSERT INTO uzytkownik_rola(id_uzytkownika, id_roli) VALUES (3, 1);
INSERT INTO uzytkownik_rola (id_uzytkownika, id_roli) VALUES (3, 2);

INSERT INTO wydzial (id_wydzialu, nazwa, adres) VALUES (1, 'Wydzial Zarzadzania', 'Powstancow Warszawy');
INSERT INTO wydzial (id_wydzialu, nazwa, adres) VALUES (2, 'Wydział Elektrotechniki i Informatyki', 'Wincentego Pola');

INSERT INTO kierunek (id_kierunku, id_wydzialu, nazwa, opis) VALUES (1, 1, 'Logistyka', 'A nie wiem w sumie');
INSERT INTO kierunek (id_kierunku, id_wydzialu, nazwa, opis) VALUES (2, 2, 'Informatyka', 'Porządny kierunek');
INSERT INTO kierunek (id_kierunku, id_wydzialu, nazwa, opis) VALUES (3, 2, 'Energetyka', 'Też spoko');



INSERT INTO uzytkownik_kierunek (id_uzytkownika, id_kierunku) VALUES (1,1);


INSERT INTO pytanie (id_pytania , tresc) VALUES (1, 'Ddziała?');
INSERT INTO pytanie (id_pytania , tresc) VALUES (2, 'Ale dobrze ddziała?');
INSERT INTO pytanie (id_pytania , tresc) VALUES (3, 'Na pewno?');


INSERT INTO odpowiedz_uzytkownika (id_odpowiedzi_uzytkownika, wartosc, id_pytania, id_uzytkownika) VALUES (1, 9, 1, 1);
INSERT INTO odpowiedz_uzytkownika (id_odpowiedzi_uzytkownika, wartosc, id_pytania, id_uzytkownika) VALUES (2, 10, 2, 1);
INSERT INTO odpowiedz_uzytkownika (id_odpowiedzi_uzytkownika, wartosc, id_pytania, id_uzytkownika) VALUES (3, 5, 3, 2);
INSERT INTO odpowiedz_uzytkownika (id_odpowiedzi_uzytkownika, wartosc, id_pytania, id_uzytkownika) VALUES (4, 2, 2, 2);

INSERT INTO wartosc_oczekiwana (id_wartosci_oczekiwanej, wartosc, id_pytania, id_kierunku) VALUES (1, 8, 1,1);
INSERT INTO wartosc_oczekiwana (id_wartosci_oczekiwanej, wartosc, id_pytania, id_kierunku) VALUES (2, 9, 2,1);
INSERT INTO wartosc_oczekiwana (id_wartosci_oczekiwanej, wartosc, id_pytania, id_kierunku) VALUES (3, 7, 3,1);
INSERT INTO wartosc_oczekiwana (id_wartosci_oczekiwanej, wartosc, id_pytania, id_kierunku) VALUES (4, 7, 1,2);
INSERT INTO wartosc_oczekiwana (id_wartosci_oczekiwanej, wartosc, id_pytania, id_kierunku) VALUES (5, 8, 2,2);
INSERT INTO wartosc_oczekiwana (id_wartosci_oczekiwanej, wartosc, id_pytania, id_kierunku) VALUES (6, 9, 3,2);



