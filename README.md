# Zwinne_Recruiter.io

Projekt programu wspomagającego pracę zespołu HR.

  - Kiedyś
  - Będzie
  - Opis

## Tech

Wykorzystane technologie

* AngularJS - wersja 5
* Java - 1.8
* maven
* TypeScript
* [Angular Material](https://material.angular.io/)
* [MongoDB](https://www.mongodb.com/)
* [Docker](https://www.docker.com/)
* [Travis](https://travis-ci.org/)
* [NodeJS](https://nodejs.org/) - 8.10.0
* [Yarn](https://yarnpkg.com/en/docs/install)
* [nodist](https://github.com/marcelklehr/nodist/releases)

## Installation

Instalację zaczynamy od środowiska, instalujemy:
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) oraz jave odpowiedniej wersji `(wersja w Tech)`;
* Dockera `(link w Tech)`;
* Yarn `(link w Tech)`;
* Nodist `(link w Tech)`;

## Development

Otwieramy swój ulubliony terminal, polecam [cmder](http://cmder.net/).

### IntelliJ
Mam nadzeje że z IntelliJ wszystko jasne.

### Docker
Uruchomiamy go, jesli macie najnowszą wersję Dockera, i może Win 10, to prawdopodobnie będziecie mogli uruchamiac polecenia z zwyklego terminalu, jesli nie to trzeba wszystkie skrypty uruchamiac ręcznie w Docker Terminal.

W terminale otwieramy:
```sh
$ Zwinne_Recruiter.io\util
```

I uruchamiamy po kolei scrypty
* build.bat
* start_db.bat
* update_db.bat

Po zakonczeniu pracy

* stop_db.bat

Również dojść wygodno można zarządzac docker`owymi kontenerami w Docker Kinematic.

Pamiętacjie żeby sprawdzic na jakim porcie uruchomila sie baza i podmienic, jesli trzeba, adres w `applicaion.properties`.

### Nodist

Jest on potrzebny dla uruchomienia front-end'owej części apliakcji, oraz pozwala latwo podmieniac werjse noda.

Uruchomiamy terminal i wykonujemy:

```sh
$ nodist global 8.10.0
```

### To start the project

* Uruchomiamy projekt Jawowy, domyslnie jest ona na porcie `http://localhost:8080`.

* Uruchomiamy stronę:

Wykonanie samego polecenie `yarn` jest potrzebne tylko dla zainstalowania bibliotek, nie jest ono potrzebne jesli uruchomiamy raz za razem, tylko po zainstalowaniu nowej biblioteki do apki przez innego czlonku zespolu.

Natomiast `yarn start` juz uruchamia aplikację.

```sh
$ cd Zwinne_Recruiter.io\client
$ yarn
$ yarn start
```

Domyslnie jest na porcie `http://localhost:4200`


