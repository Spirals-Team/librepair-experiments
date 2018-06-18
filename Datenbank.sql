-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 21. Jan 2018 um 20:46
-- Server-Version: 10.1.29-MariaDB
-- PHP-Version: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `infswt`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `calendar`
--

CREATE TABLE `calendar` (
  `CALENDAR_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `calendar_termin`
--

CREATE TABLE `calendar_termin` (
  `Calendar_Termin_ID` int(11) NOT NULL,
  `TERMIN_ID` int(11) NOT NULL,
  `CALENDAR_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `category`
--

CREATE TABLE `category` (
  `CATEGORY_ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Color` smallint(6) NOT NULL,
  `Icon` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `category`
--

INSERT INTO `category` (`CATEGORY_ID`, `Name`, `Color`, `Icon`) VALUES
(1, 'Privat', 1, 'Icon-Privat'),
(2, 'Uni', 2, 'Icon-Uni'),
(3, 'Familie', 3, 'Icon-Familie');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `category_termin`
--

CREATE TABLE `category_termin` (
  `CATEGORY_TERMIN_ID` int(11) NOT NULL,
  `TERMIN_ID` int(11) NOT NULL,
  `CATEGORY_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `termin`
--

CREATE TABLE `termin` (
  `TERMIN_ID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Start` date NOT NULL,
  `StartZeit` time NOT NULL,
  `Ende` date NOT NULL,
  `EndeZeit` time NOT NULL,
  `allDay` tinyint(1) NOT NULL,
  `Ort` varchar(100) DEFAULT NULL,
  `RepeatBool` tinyint(1) NOT NULL,
  `RepeatTime` varchar(12) DEFAULT NULL,
  `Cancel` tinyint(1) DEFAULT NULL,
  `Attachement` blob,
  `Note` varchar(200) DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL,
  `Reminder` tinyint(1) DEFAULT NULL,
  `VCard` int(11) DEFAULT NULL,
  `CancelMsg` varchar(50) DEFAULT NULL,
  `ReminderDate` date DEFAULT NULL,
  `ReminderTime` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Firstname` varchar(45) NOT NULL,
  `Lastname` varchar(45) NOT NULL,
  `ConfirmedUser` tinyint(4) DEFAULT NULL,
  `UserPreferences` int(11) DEFAULT NULL,
  `WeatherReport` blob,
  `CALENDAR_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userpreferences`
--

CREATE TABLE `userpreferences` (
  `USER_PREFERENCES_ID` int(11) NOT NULL,
  `Province` varchar(45) DEFAULT NULL,
  `University` varchar(45) DEFAULT NULL,
  `Course` varchar(45) DEFAULT NULL,
  `Semester` varchar(45) DEFAULT NULL,
  `Music` varchar(45) DEFAULT NULL,
  `Gender` varchar(45) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `USER_ID` int(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `vcard`
--

CREATE TABLE `vcard` (
  `VCARD_ID` int(11) NOT NULL,
  `Firstname` varchar(45) NOT NULL,
  `Lastname` varchar(45) NOT NULL,
  `TelNr` varchar(45) NOT NULL,
  `Office` varchar(45) NOT NULL,
  `Title` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Note` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Tabellenstruktur für Tabelle `UserPreferences`
--

CREATE TABLE `UserPreferences` (
  `User_Preferences_ID` int(11) NOT NULL,
  `province` varchar(45) NOT NULL,
  `university` varchar(45) NOT NULL,
  `course` varchar(45) NOT NULL,
  `semester` int(11) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `age` int(11) NOT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Daten für Tabelle `vcard`
--

INSERT INTO `vcard` (`VCARD_ID`, `Firstname`, `Lastname`, `TelNr`, `Office`, `Title`, `Email`, `Note`) VALUES
(1, 'Malte', 'Rusko', '0451 4094801', 'Office 1', 'Dr.', 'malte.rusko@stud.fh-luebeck.de', 'Erste VCard!'),
(2, 'Philipp', 'Drath', '0451 4094802', 'Office 2', 'Prof', 'philipp.drath@stud.fh-luebck.de', 'Zweite VCard!'),
(3, 'Sergej', 'Makschanow', '0451 4094803', 'Office 3', 'Prof. rer. nat.', 'sergej.makschanow@stud.fh-luebeck.de', 'Die dritte VCard!');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `calendar`
--
ALTER TABLE `calendar`
  ADD PRIMARY KEY (`CALENDAR_ID`),
  ADD KEY `USER_ID` (`USER_ID`);

--
-- Indizes für die Tabelle `calendar_termin`
--
ALTER TABLE `calendar_termin`
  ADD PRIMARY KEY (`Calendar_Termin_ID`),
  ADD KEY `TERMIN_ID` (`TERMIN_ID`),
  ADD KEY `CALENDAR_ID` (`CALENDAR_ID`);

--
-- Indizes für die Tabelle `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`CATEGORY_ID`);

--
-- Indizes für die Tabelle `category_termin`
--
ALTER TABLE `category_termin`
  ADD PRIMARY KEY (`CATEGORY_TERMIN_ID`),
  ADD KEY `TERMIN_ID` (`TERMIN_ID`),
  ADD KEY `CATEGORY_ID` (`CATEGORY_ID`);

--
-- Indizes für die Tabelle `termin`
--
ALTER TABLE `termin`
  ADD PRIMARY KEY (`TERMIN_ID`),
  ADD KEY `VCard` (`VCard`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`USER_ID`),
  ADD KEY `CALENDAR_ID` (`CALENDAR_ID`),
  ADD KEY `UserPreferences` (`UserPreferences`);

--
-- Indizes für die Tabelle `userpreferences`
--
ALTER TABLE `userpreferences`
  ADD PRIMARY KEY (`USER_PREFERENCES_ID`),
  ADD KEY `USER_ID` (`USER_ID`);

--
-- Indizes für die Tabelle `vcard`
--
ALTER TABLE `vcard`
  ADD PRIMARY KEY (`VCARD_ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `calendar`
--
ALTER TABLE `calendar`
  MODIFY `CALENDAR_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `calendar_termin`
--
ALTER TABLE `calendar_termin`
  MODIFY `Calendar_Termin_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `category`
--
ALTER TABLE `category`
  MODIFY `CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `category_termin`
--
ALTER TABLE `category_termin`
  MODIFY `CATEGORY_TERMIN_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `termin`
--
ALTER TABLE `termin`
  MODIFY `TERMIN_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `userpreferences`
--
ALTER TABLE `userpreferences`
  MODIFY `USER_PREFERENCES_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `vcard`
--
ALTER TABLE `vcard`
  MODIFY `VCARD_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `calendar`
--
ALTER TABLE `calendar`
  ADD CONSTRAINT `calendar_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);

--
-- Constraints der Tabelle `calendar_termin`
--
ALTER TABLE `calendar_termin`
  ADD CONSTRAINT `calendar_termin_ibfk_1` FOREIGN KEY (`TERMIN_ID`) REFERENCES `termin` (`TERMIN_ID`),
  ADD CONSTRAINT `calendar_termin_ibfk_2` FOREIGN KEY (`CALENDAR_ID`) REFERENCES `calendar` (`CALENDAR_ID`);

--
-- Constraints der Tabelle `category_termin`
--
ALTER TABLE `category_termin`
  ADD CONSTRAINT `category_termin_ibfk_1` FOREIGN KEY (`TERMIN_ID`) REFERENCES `termin` (`TERMIN_ID`),
  ADD CONSTRAINT `category_termin_ibfk_2` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`CATEGORY_ID`);

--
-- Constraints der Tabelle `termin`
--
ALTER TABLE `termin`
  ADD CONSTRAINT `termin_ibfk_1` FOREIGN KEY (`VCard`) REFERENCES `vcard` (`VCARD_ID`);

--
-- Constraints der Tabelle `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`CALENDAR_ID`) REFERENCES `calendar` (`CALENDAR_ID`),
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`UserPreferences`) REFERENCES `userpreferences` (`USER_PREFERENCES_ID`);

--
-- Constraints der Tabelle `userpreferences`
--
ALTER TABLE `userpreferences`
  ADD CONSTRAINT `userpreferences_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
