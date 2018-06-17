# Contributing
Wenn Sie an diesem Repository mitarbeiten wollen, diskutieren Sie bitte erst die Änderungen die Sie wünschen über einen `issue` oder via E-Mail, bevor Sie Änderungen vornehmen.

Bitte beachten Sie, dass wir ein `code of conduct` haben, bitte halten Sie sich an die darin hinterlegten Vereinbarungen.

## Pull Request Process

1. Stellen Sie sicher, dass alle Installation und Build-Abhängikeiten entfernt sind 
2. Aktuallisieren Sie die README.md ggf. mit sinnvollen Erweiterungen
3. Schreiben Sie Softwaretests für die neuen Komponenten nach den unten definierten Anforderungen
4. Ihr PullRequest wird akzeptiert wenn 2 Entwickler ihren Änderungen zustimmen.
<!-- 3. Aktualisiere die Versionsnummer in allen Datein.  -->

## Code of Conduct

### Our Pledge

Um eine offene und einladende Umgebung zu fördern, haben wir uns als
Mitwirkende und Betreuer verpflichtet, den Teilnehmern an unserem Projekt eine belästigungsfreie und angenehme Athmosphäre zu schaffen. Unabhängig von Alter und Körpergröße, Behinderung, ethnischer Zugehörigkeit, Geschlechtsidentität und -ausdruck, Erfahrungsniveau, Nationalität, persönliches Aussehen, Rasse, Religion oder sexuelle Identität und
Orientierung.

### Our Standards

Beispiele für Verhaltensweisen die ein positiven Eindruck schaffen:

* Freundliche und verständliche Sprache
* Respektieren unterschiedlicher Erfahrungsstände 
* Konstruktive Kritik akzeptieren
* Empathie gegenüber anderen Community-Mitgliedern zeigen

Beispiele für inakzeptables Verhalten 

* Verwendung sexistischer Sprache bzw. Bilder 
* beleidigende bzw. abfällige Kommentare
* Öffentliche oder private Belästigung
* Veröffentlichung privater Informationen anderer Personen
* Andere Verhaltensweisen, als unangemessen angesehen werden könnten 

### Scope of Testing

Um ein erfolgreichen einen PullRequest zu stellen, müssen folgende Punkte beachtet werden. 
* [J-Unit](https://junit.org/junit4/) Tests (in der Version 4) für die neuen Komponenten nach dem Verfahren der Grenzwertanalyse müssen vorhanden [about Grenzwertanalyse](https://de.wikipedia.org/wiki/Dynamisches_Software-Testverfahren#Grenzwertanalyse) 
* Für neu implementierte Userstorys müssen UI Test in [Selenium](https://www.seleniumhq.org/) geschrieben werden. 
* Es muss immer der Normalablauf und alle im Lastenheft behandelten Atlernativabläufe getestet werden.
* alle Tests müssen auf einem HeadlessCI über [Maven](https://maven.apache.org/) ausführbar sein!  

### Definition of Done
* Die Akzeptanzkriterien der User Story sind erfüllt
* Alle Tests sind geschrieben und laufen grün
* Der Code ist im Repository erfolgreich eingecheckt
* Ein Code Review wurde durchgeführt 
* Coding Guidelines und Standards wurden eingehalten
* Es sind keine kritischen Bugs offen
