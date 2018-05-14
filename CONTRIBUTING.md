# Contributing
Wenn Sie an diesem Repository mitarbeiten wollen, duskutieren Sie bitte erst die Änderungen die Sie wünschen über einen `issue` oder via E-Mail, bevor Sie Änderungen vornehmen.

Bitte beachten Sie das wir ein `code of conduct` haben, bitte halten Sie sich an die darin hinterlegten Vereinbarungen.

## Pull Request Process

1. Stellen Sie sicher das alle Installation und Build-Abhängikeiten entfernt sind 
2. Aktuallisieren Sie die README.md ggf. mit sinnvollen Erweiterungen
3. Schreiben Sie Softwaretests für die neuen Komponenten nach den unten definierten Anforderungen
4. Ihr PullRequest wird akzeptiert wenn 2 entwickler ihren ändereungen zustimmen.
<!-- 3. Aktualisiere die Versionsnummer in allen Datein.  -->

## Code of Conduct

### Our Pledge

Um eine offene und einladende Umgebung zu fördern, haben wir uns als
Mitwirkende und Betreuer verpflichtet, den Teilnehmern an unserem Projekt eine belästigungsfreie und angenehme Athmosphäre zu schaffen. Unabhängig von Alter und Körper
Größe, Behinderung, ethnische Zugehörigkeit, Geschlechtsidentität und -ausdruck, Erfahrungsniveau, Nationalität, persönliches Aussehen, Rasse, Religion oder sexuelle Identität und
Orientierung.

### Our Standards

Beispiele für Verhaltensweisen die ein positiven eindruck schaffen:

* Freundliche und verständliche Sprache
* Respektieren unterschiedlicher Erfahrungsstaände 
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
* Für neu implementierte Userstorys muss ein UI Test in [Selenium](https://www.seleniumhq.org/) geschrieben werden. 
* Es muss immer der Normalablauf und alle im Lastenheft behandelten Atlernativabläufe getestet werden.
* alle Tests müssen auf einem HeadlessCI über [Maven](https://maven.apache.org/) ausführbar sein!  
