-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 18, 2023 alle 13:14
-- Versione del server: 10.4.27-MariaDB
-- Versione PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ticketing`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `change_pass`
--

CREATE TABLE `change_pass` (
  `id_utente` int(11) NOT NULL,
  `pass` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `class`
--

CREATE TABLE `class` (
  `ID_Studente` int(11) NOT NULL,
  `ID_Ticket` int(11) NOT NULL,
  `LVLCompetenzaStudente` varchar(30) NOT NULL,
  `QueryStatus` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `class`
--

INSERT INTO `class` (`ID_Studente`, `ID_Ticket`, `LVLCompetenzaStudente`, `QueryStatus`) VALUES
(1, 2, 'ALTO', 'Inclass'),
(2, 2, 'NO', 'Incoda'),
(2, 2, 'BASSO', 'Inclass');

-- --------------------------------------------------------

--
-- Struttura della tabella `social`
--

CREATE TABLE `social` (
  `id` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `descrizione` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ticket`
--

CREATE TABLE `ticket` (
  `ID` int(11) NOT NULL,
  `Materia` varchar(30) NOT NULL,
  `Descrizione` varchar(254) NOT NULL,
  `Disponibilità_Giorni` varchar(255) NOT NULL,
  `ID_Utente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `ticket`
--

INSERT INTO `ticket` (`ID`, `Materia`, `Descrizione`, `Disponibilità_Giorni`, `ID_Utente`) VALUES
(1, 'EDFISICA', 'Strong', 'Giovedi', 1),
(2, 'ITALIANO', 'NONONO', 'Venerdi, Domenica', 1),
(3, 'ECONOMIA\r\n                    ', 'CIAO', '', 2),
(4, 'MATEMATICA', 'NONONO', '', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `ID` int(11) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(254) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `email` varchar(35) NOT NULL,
  `anno_classe` int(11) NOT NULL,
  `Sezione` varchar(10) NOT NULL,
  `zona_geografica` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`ID`, `Username`, `Password`, `Nome`, `Cognome`, `email`, `anno_classe`, `Sezione`, `zona_geografica`) VALUES
(1, 'Username', 'ciao', 'ggg', 'ggg', 'ggg', 4, 'gdf', 'gsdf'),
(2, 'mattia', 'age', 'ag', 'hrts', 'gaer', 4, 'ger', 'gaer');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `change_pass`
--
ALTER TABLE `change_pass`
  ADD PRIMARY KEY (`id_utente`);

--
-- Indici per le tabelle `class`
--
ALTER TABLE `class`
  ADD KEY `ID_Studente` (`ID_Studente`),
  ADD KEY `ID_Ticket` (`ID_Ticket`);

--
-- Indici per le tabelle `social`
--
ALTER TABLE `social`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indici per le tabelle `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Utente` (`ID_Utente`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `social`
--
ALTER TABLE `social`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `ticket`
--
ALTER TABLE `ticket`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `change_pass`
--
ALTER TABLE `change_pass`
  ADD CONSTRAINT `change_pass_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`ID`);

--
-- Limiti per la tabella `class`
--
ALTER TABLE `class`
  ADD CONSTRAINT `class_ibfk_1` FOREIGN KEY (`ID_Studente`) REFERENCES `utente` (`ID`),
  ADD CONSTRAINT `class_ibfk_2` FOREIGN KEY (`ID_Ticket`) REFERENCES `ticket` (`ID`);

--
-- Limiti per la tabella `social`
--
ALTER TABLE `social`
  ADD CONSTRAINT `social_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`ID`);

--
-- Limiti per la tabella `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`ID_Utente`) REFERENCES `utente` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
