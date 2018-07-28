# Installation
Die nachfolgenden Kapitel sollten in der angegebenen Reihenfolge umgesetzt werden.

## Betriebssystem
Für den Raspberry Pi existieren verschiedene, darauf zugeschnittene, Linux-Distributionen (Images), wobei [Raspbian](https://www.raspberrypi.org/downloads/raspbian) vermutlich das geläufigste ist (auf dieses beziehe ich mich nachfolgend). 
Damit der *Smart Appliance Enabler* lauffähig ist, muss bei der Wahl des Images ist darauf geachtet werden, dass dieses eine Java 8-Runtime enthält oder dass diese nachinstallierbar ist. Beim Raspbian-Image ist die *Lite-Version ausreichend*, sodass man eine *4GB-SD-Karte* verwenden kann.

Mit dem nachfolgenden Befehl kann man unter Linux ein Image auf eine SD-Karte schreiben:
```
axel@tpw520:~/Downloads/raspberry$ sudo dd bs=4M if=2018-03-13-raspbian-stretch-lite.img of=/dev/mmcblk0 status=progress oflag=sync
[sudo] password for axel: 
1858076672 bytes (1.9 GB, 1.7 GiB) copied, 205.445 s, 9.0 MB/s 
443+0 records in
443+0 records out
1858076672 bytes (1.9 GB, 1.7 GiB) copied, 205.598 s, 9.0 MB/s
```
Sollte der Raspberry mit der SD-Karte nicht starten, kann es durchaus an der SD-Karte selbst liegen (diese Situation hatte ich gerade selbst). In diesem Fall einfach einen anderen SD-Karten-Typ verwenden (gute Erfahrungen habe ich mit SanDisk gemacht). Einen erfolgreichen Start erkennt man leicht daran, dass die grüne LED flackert/leuchtet (= Zugriff auf die SD-Karte).

### SSH-Client
Die Interaktion mit dem Raspberry Pi erfolt über SSH (Secure Shell), d.h. über ein Fenster vergleichbar der Windows-Eingabeaufforderung. Während bei Linux ein SSH-Client zur Standardausrüstung gehört muss dieser unter Windows separat installiert werden. Eine Anleitung dafür findet sich im Artikel [SSH using Windows](https://www.raspberrypi.org/documentation/remote-access/ssh/windows.md).

In den nachfolgenden Kapiteln sind diverse Befehle aufgeführt. Am Prompt ```pi@raspberrypi ~ $``` lässt sich leicht erkennen, dass der nachfolgende Befehl in der SSH-Shell auf dem Raspberry Pi einzugeben ist (```pi``` ist dabei der Username, der für die Anmeldung benutzt wurde und ```raspberrypi``` ist der Hostname. ```~``` symbolisiert das Home-Verzeichnis des Users - ansonsten wird das aktuelle Verzeichnis direkt angezeigt).

Der eigentliche, einzugebende Befehl beginnt dabei erst nach dem ```$```-Zeichen!

### SSH-Zugriff
Auf neueren Images ist SSH aus Sicherheitsgründen standardmäßig deaktiviert. Zum Aktivieren gibt es verschiedene Möglichkeiten (siehe https://linuxundich.de/raspberry-pi/ssh-auf-dem-raspberry-pi-aktivieren-jetzt-unter-raspian-noetig oder 
https://kofler.info/geaenderte-ssh-server-konfiguration-von-raspbian), wobei ich den nachfolgend beschriebenen Weg bevorzuge (geht mit diesen Befehlen so nur unter Linux):

1. Mounten der Boot-Partition der SD-Karte
```
axel@tpw520:~$ sudo mount /dev/mmcblk0p1 /media/axel/tmp
```
2. Erzeugen einer leeren Datei mit dem Namen ```ssh```:
```
axel@tpw520:~$ sudo touch /media/axel/tmp/ssh
```
3. Unmounten der gemounteten Partition der SD-Karte
```
axel@tpw520:~$ sudo umount /media/axel/tmp
```
Nachdem der Raspberry Pi mit der so modifizierten SD-Karte gebootet wurde, sollte der Zugriff mit SSH möglich sein.
Dabei nicht vergessen, den Raspberry Pi über ein Ethernet-Kabel mit dem Router zu verbinden!
Für den zugriff benötigt man natürllich die IP-Adresse oder den Hostnamen, der dem Raspberry Pi vom Router zugewiesen wurde (in meinem Beispiel ist das ```raspi```)
```
axel@tpw520:~/Downloads/raspberry$ ssh pi@raspi
pi@raspi's password: 
Linux raspberrypi 4.9.80-v7+ #1098 SMP Fri Mar 9 19:11:42 GMT 2018 armv7l

The programs included with the Debian GNU/Linux system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Debian GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent
permitted by applicable law.

SSH is enabled and the default password for the 'pi' user has not been changed.
This is a security risk - please login as the 'pi' user and type 'passwd' to set a new password.

pi@raspberrypi:~ $
```

### WLAN einrichten (nur Raspberry Pi 3)
Soll der Raspberry Pi über WLAN statt über Ethernet angebunden werden, müssen SSID und Passwort in die Datei ```/etc/wpa_supplicant/wpa_supplicant.conf``` eingetragen werden. Eine genaue Beschreibung findet sich im Kapitel [ADDING THE NETWORK DETAILS TO THE RASPBERRY PI](https://www.raspberrypi.org/documentation/configuration/wireless/wireless-cli.md).

### Hostnamen ändern
Unabhängig von dem Hostnamen, über den der Raspberry im lokalen Netzwerk erreicht werden kann, ist sein Hostname standardmäßig ```raspberry``` (auch sichtbar am Prompt: ```pi@raspberrypi:~ $```). Vor allem, wenn man mehrere Raspberries im Netz hat, will man auch am Prompt sehen, auf welchem Raspberry man gerade die Befehle eingibt. Zum Ändern des Hostnames kann nachfolgender Befehl auf dem Raspberry verwendet werden:
```
pi@raspberrypi ~ $ sudo hostname -b raspi3
```

### Zeitzone einstellen
Damit Zeitangaben zum Schalten der Geräte richtig interpretiert werden, sollte die Zeitzone des Raspberry auf die lokale Zeit gesetzt sein (nicht UTC!). Das kann mit folgendende Befehlen erreicht werden:
```
pi@raspberrypi ~ $ sudo /bin/bash -c "echo 'Europe/Berlin' > /etc/timezone"
pi@raspberrypi ~ $ sudo cp /usr/share/zoneinfo/Europe/Berlin /etc/localtime
```

### Java 8 installieren
Die Installation des vom *Smart Appliance Enabler* benötigten Java 8 erfolgt ganz einfach mit
```
pi@raspberrypi ~ $ sudo apt-get update
pi@raspberrypi ~ $ sudo apt-get install oracle-java8-jdk
```
Die Java-Version läßt sich mit folgendem Befehl überprüfen:
```
pi@raspberrypi:~ $ java -version
java version "1.8.0_65"
Java(TM) SE Runtime Environment (build 1.8.0_65-b17)
Java HotSpot(TM) Client VM (build 25.65-b01, mixed mode)
```

## Smart Appliance Enabler
Die Installation des *Smart Appliance Enabler* besteht darin, folgende Dateien auf den Raspberry zu kopieren:
* das Start-Script
* die Datei ```SmartApplianceEnabler-*.war``` mit dem eigentlichen Programmcode (heruntergeladenes Release oder aus Sourcen gebaut)

Zunächst werden Start-Script und zugehörige Konfigurationsdateien auf den Raspberry heruntergeladen und gleich die Berechtigungen für dieses Dateien gesetzt:
```
axel@tpw520:/data/git/SmartApplianceEnabler$ ssh pi@raspi
pi@raspi's password: 

The programs included with the Debian GNU/Linux system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Debian GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent
permitted by applicable law.
Last login: Sun Dec  6 19:17:12 2015

pi@raspberrypi ~ $ sudo wget https://github.com/camueller/SmartApplianceEnabler/raw/master/run/etc/init.d/smartapplianceenabler -P /etc/init.d
pi@raspberrypi ~ $ sudo chown root.root /etc/init.d/smartapplianceenabler
pi@raspberrypi ~ $ sudo chmod 755 /etc/init.d/smartapplianceenabler

pi@raspberrypi ~ $ sudo wget https://github.com/camueller/SmartApplianceEnabler/raw/master/run/etc/default/smartapplianceenabler -P /etc/default
pi@raspberrypi ~ $ sudo chown root.root /etc/default/smartapplianceenabler
pi@raspberrypi ~ $ sudo chmod 644 /etc/default/smartapplianceenabler

```
In der Datei ```/etc/default/smartapplianceenabler``` finden sich die Konfigurationseinstellungen für den Dienst *smartapplianceenabler*. Die darin befindlichen Parameter sind in der Datei selbst dokumentiert. Normalerweise sollte man die Datei unverändert lassen können.

Damit der Dienst *smartapplianceenabler* beim Systemstart ebenfalls gestartet wird, muss folgender Befehl ausgeführt werden:
```
pi@raspberrypi ~ $ sudo systemctl enable smartapplianceenabler.service
```
Nach diesen Änderungen muss der [Systemd](https://de.wikipedia.org/wiki/Systemd) dazu gebracht werden, die Service-Konfigurationen neu zu lesen:
```
pi@raspberrypi ~ $ sudo systemctl daemon-reload
```
Die erfolgreiche Reistrierung des Dienstes *smartapplianceenabler* kann wie folgt überprüft werden:
```
pi@raspberrypi ~ $ systemctl list-units|grep smart
smartapplianceenabler.service                                            loaded activating start     start LSB: Start Smart Appliance Enabler.
```
Falls die zweite Zeile nicht angezeigt wird, sollte der Raspberry neu gestartet werden.

Der *Smart Appliance Enabler* selbst und seine Konfigurationsdateien sollten im Verzeichnis ```/app``` abgelegt werden, das zunächst erstellt werden muss:
```
pi@raspberrypi ~ $ sudo mkdir /app
pi@raspberrypi ~ $ sudo chown pi.pi /app
```
Für die Konfiguration des Loggings wird die Datei ```logback-spring.xml``` benötigt, die einfach heruntergeladen werden kann:
```
pi@raspberrypi ~ $ wget https://github.com/camueller/SmartApplianceEnabler/raw/master/logback-spring.xml -P /app
```
Als nächstes wird die Datei ```SmartApplianceEnabler-*.war``` mit dem eigentlichen Programmcode heruntergeladen:
```
pi@raspberrypi ~ $ wget https://github.com/camueller/SmartApplianceEnabler/releases/download/v1.2.0/SmartApplianceEnabler-1.2.0.war -P /app
```
Jetzt sollte man den *Smart Appliance Enabler* starten können. Auf einem Raspberry Pi 2 Model B dauert der Start ca. 30 Sekunden.  Dabei sollte man folgende Ausgaben zu sehen bekommen:
```
pi@raspberrypi ~ $ sudo /etc/init.d/smartapplianceenabler start
[ ok ] Starting smartapplianceenabler (via systemctl): smartapplianceenabler.service.
```
Sollten statt des ```ok``` andere Meldungen angezeigt werden, helfen [diese Hinweise](Troubleshooting_DE.md) bei der Lokalisierung des Problems.

Eigentlich lässt man Dienste wie *smartapplianceenabler* nicht unter dem Benutzer *root* laufen. Allerdings habe ich bisher keine Möglichkeit gefunden, die Rechte für den Zugriff auf die GPIO-Ports so zu setzen, dass diese auch für andere Benutzer möglich ist.

Wenn der *Smart Appliance Enabler* jetzt läuft, muss als Nächstes die [Konfiguration](Configuration_DE.md) vorgenommen werden.
