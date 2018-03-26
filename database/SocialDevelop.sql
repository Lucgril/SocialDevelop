-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Creato il: Mar 25, 2018 alle 18:05
-- Versione del server: 10.1.19-MariaDB
-- Versione PHP: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `SocialDevelop`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `domanda`
--

CREATE TABLE `domanda` (
  `id` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  `task` int(11) NOT NULL,
  `data` date DEFAULT NULL,
  `stato` varchar(30) NOT NULL DEFAULT 'in attesa',
  `valutazione` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `domanda`
--

INSERT INTO `domanda` (`id`, `utente`, `task`, `data`, `stato`, `valutazione`) VALUES
(3, 2, 13, '2017-06-01', 'accettato', 5),
(4, 3, 13, '2017-06-03', 'Adesione richiesta', 0),
(7, 2, 14, '2017-06-08', 'accettato', 0),
(8, 5, 18, '2017-07-08', 'Adesione richiesta', 0),
(9, 6, 9, '2017-06-09', 'Adesione richiesta', 0),
(12, 4, 9, '2017-06-14', 'Adesione richiesta', 2),
(13, 3, 9, '2017-06-01', 'Adesione richiesta', 0),
(19, 2, 22, '2017-07-04', 'Adesione richiesta', 0),
(20, 2, 10, '2017-07-05', 'Adesione richiesta', 0),
(23, 13, 30, '2017-07-06', 'Adesione richiesta', 0),
(45, 2, 32, '2017-07-06', 'Adesione richiesta', 0),
(49, 5, 19, '2017-07-06', 'rifiutato', 0),
(51, 5, 32, '2017-07-06', 'Adesione richiesta', 0),
(54, 5, 15, '2017-07-06', 'Adesione richiesta', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `files`
--

CREATE TABLE `files` (
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `size` int(11) NOT NULL,
  `localfile` varchar(255) NOT NULL,
  `ID` int(11) NOT NULL,
  `digest` varchar(255) NOT NULL,
  `utente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `files`
--

INSERT INTO `files` (`name`, `type`, `size`, `localfile`, `ID`, `digest`, `utente`) VALUES
('curriculum_0', 'application/pdf', 1243424, 'curriculum_0', 1, '31-4254-26-317-50-1598-6133-933560-1212622-301440', 3),
('curriculum_4', 'text/plain', 27, 'curriculum_4', 2, '98122198-39119-98-9550-6078-425-38-560-34-1009951', 4),
('curriculum_5', 'text/plain', 27, 'curriculum_5', 3, '98122198-39119-98-9550-6078-425-38-560-34-1009951', 5),
('curriculum_6', 'text/plain', 27, 'curriculum_6', 4, '98122198-39119-98-9550-6078-425-38-560-34-1009951', 6),
('curriculum_7', 'application/pdf', 1243424, 'curriculum_7', 5, '31-4254-26-317-50-1598-6133-933560-1212622-301440', 7),
('curriculum_11', 'application/pdf', 1243424, 'curriculum_5682635570847132085', 6, '31-4254-26-317-50-1598-6133-933560-1212622-301440', 11),
('curriculum_12', 'application/pdf', 1243424, 'curriculum_2213698962304876755', 7, '31-4254-26-317-50-1598-6133-933560-1212622-301440', 12),
('curriculum_14', 'application/pdf', 1243424, 'curriculum_99699770684950230', 8, '31-4254-26-317-50-1598-6133-933560-1212622-301440', 14),
('curriculum_16', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 16579, 'curriculum_3592719513918473316', 9, '72-10641-5330-3168-185381-103124-27479576-114-3925-55', 16);

-- --------------------------------------------------------

--
-- Struttura della tabella `messaggio`
--

CREATE TABLE `messaggio` (
  `id` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  `progetto` int(11) NOT NULL,
  `testo` varchar(100) NOT NULL,
  `data` date DEFAULT NULL,
  `tipo` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `messaggio`
--

INSERT INTO `messaggio` (`id`, `utente`, `progetto`, `testo`, `data`, `tipo`) VALUES
(1, 2, 11, 'dede', NULL, 'pubblico'),
(2, 2, 11, 'Messaggio privato', NULL, 'privato'),
(3, 2, 11, 'Messaggio privato', NULL, 'privato'),
(4, 2, 28, 'Pubblico', NULL, 'pubblico'),
(5, 2, 28, 'Privato', NULL, 'privato'),
(6, 2, 11, 'ddd', NULL, 'pubblico'),
(7, 2, 11, 'ddd', NULL, 'pubblico'),
(8, 2, 11, '', NULL, 'privato'),
(9, 5, 36, 'Ciao', NULL, 'pubblico'),
(10, 2, 28, 'Provaprivato2', '2017-07-05', 'privato'),
(11, 2, 28, 'ProvaPubblico', '2017-07-05', 'pubblico'),
(12, 13, 38, 'Bel progetto', '2017-07-06', 'pubblico'),
(13, 2, 11, 'Messaggio Publlico', '2017-07-11', 'pubblico'),
(14, 2, 11, 'Messaggio Publlico', '2017-07-11', 'pubblico'),
(15, 2, 11, 'Messaggio Publlico', '2017-07-11', 'pubblico'),
(16, 16, 39, 'ciao ragazzi!!', '2017-07-20', 'privato');

-- --------------------------------------------------------

--
-- Struttura della tabella `progetto`
--

CREATE TABLE `progetto` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descrizione` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `progetto`
--

INSERT INTO `progetto` (`id`, `nome`, `descrizione`) VALUES
(11, 'Progetto', 'Descrizione per il progetto numero 1'),
(12, 'Progetto2', 'Descrizione2'),
(28, 'SocialDevelop', 'Questo è un progetto fatto in gruppi all''università.'),
(29, 'Mobile app "pippo"', 'Questo progetto ha come obiettivo la creazione di un applicazione mobile.'),
(30, 'SocialDevelop2', 'Aggiornamento del progetto SocialDevelop alla versione 2.2'),
(31, 'Creazione VideoGame', 'L''idea di base sarebbe ricreare un video gioco molto simile a PAc-Man'),
(32, 'CSS Race', 'Questa è una sfida per tutti gli amanti dei css. '),
(33, 'New Project', 'This is a new project'),
(35, 'Creazione sito univq', 'Salve, vorrei chiedere il vostro aiuto per la creazione di un sito web per l''università del L''Aquila'),
(36, 'Progetto Luca', 'Questo Progeto è fatto da Luca'),
(37, 'Cocacola', 'Realizzazione sito web per l''azienda CocaCola'),
(38, 'Robotic Hover', 'Progetto di robotica'),
(39, 'twitter', 'plugin per twitter');

-- --------------------------------------------------------

--
-- Struttura della tabella `proposta`
--

CREATE TABLE `proposta` (
  `id` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  `task` int(11) NOT NULL,
  `data` date DEFAULT NULL,
  `stato` varchar(30) NOT NULL DEFAULT 'in attesa',
  `valutazione` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `proposta`
--

INSERT INTO `proposta` (`id`, `utente`, `task`, `data`, `stato`, `valutazione`) VALUES
(1, 3, 11, '2017-07-02', 'accettato', 0),
(2, 1, 11, '2017-07-11', 'Adesione richiesta', 0),
(4, 3, 12, '2017-07-08', 'Adesione richiesta', 0),
(6, 2, 22, '2017-07-14', 'accettato', 0),
(8, 7, 9, '2017-06-08', 'accettato', 0),
(9, 5, 9, '2017-06-03', 'accettato', 5),
(12, 1, 30, '2017-07-06', 'Adesione richiesta', 0),
(13, 2, 30, '2017-07-06', 'Adesione richiesta', 0),
(14, 5, 30, '2017-07-06', 'accettato', 0),
(15, 4, 31, '2017-07-06', 'Adesione richiesta', 0),
(16, 5, 13, '2017-07-10', 'Adesione richiesta', 0),
(17, 6, 20, '2017-07-11', 'Adesione richiesta', 0),
(21, 2, 19, '2017-07-11', 'Adesione richiesta', 0),
(22, 6, 19, '2017-07-11', 'Adesione richiesta', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `skill`
--

CREATE TABLE `skill` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `skill`
--

INSERT INTO `skill` (`id`, `nome`) VALUES
(3, 'Java'),
(4, 'Photoshop'),
(5, 'PHP'),
(6, 'UML'),
(7, 'C++');

-- --------------------------------------------------------

--
-- Struttura della tabella `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `descrizione` varchar(100) NOT NULL,
  `collaboratori` int(11) NOT NULL,
  `data_inizio` date DEFAULT NULL,
  `data_fine` date DEFAULT NULL,
  `progetto` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `collaboratoritot` double DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `task`
--

INSERT INTO `task` (`id`, `nome`, `descrizione`, `collaboratori`, `data_inizio`, `data_fine`, `progetto`, `tipo`, `collaboratoritot`) VALUES
(9, 'Aggiornamento Layout', 'Gli sviluppatori devono elaborare la nuova grafica', 8, '2017-06-15', '2017-06-22', 30, 1, 0),
(10, 'ToDoList', 'Prima definizione di tutte le componenti necessarie', 2, '2017-06-29', '2018-06-28', 31, 2, 0),
(11, 'Gara di velocità', 'Gara di velocità nella creazione dei css per un progilo utente', 3, '2017-06-21', '2017-06-30', 32, 1, 0),
(12, 'Competizione estetica', 'Creazione di un css per un profilo utente. Il più bel profilo riceverà massima valutazione', 4, '2017-06-21', '2017-06-30', 32, 1, 0),
(13, 'Primo task creato', 'In questo task tutti dovranno collaborare alla creazione di un database', 10, '2017-01-07', '2018-01-07', 11, 2, 0),
(14, 'Secondo task', 'In questo task è molto importante il lavoro di gruppo', 3, '2017-06-29', '2017-06-30', 11, 1, 0),
(15, 'Nome task', 'Descrizione task', 6, '2017-07-01', '2017-07-12', 33, 2, 0),
(16, 'Nome task2', 'Descrizione task2', 6, '2017-07-01', '2017-07-12', 33, 2, 0),
(18, 'Creazione Db', 'Creare un db adeguato alle esegenze del dipartimento di informatica', 4, '2017-07-06', '2017-07-20', 35, 2, 0),
(19, 'Grafica', 'Intera creazione dei css per un sito web stile forum', 3, '2017-01-07', '2018-01-07', 11, 1, 0),
(20, 'Task Pippo', 'Questo task è stato fatto per aiutare pippo ', 4, '2017-08-09', '2018-01-07', 11, 1, 0),
(21, 'Scrittura documentazione', 'Scrivere la documentazione del progetto', 6, '2017-07-13', '2017-07-28', 11, 3, 0),
(22, 'Task 1 Luca', 'Questo è il primo Task di luca', 1, '2017-07-01', '2017-07-30', 36, 2, 1),
(23, 'Modifica immagini  ', 'In questo task gli sviluppatori devono modificare delle immagini fornite dal coordinatore.', 4, '2017-07-01', '2017-07-30', 36, 2, 0),
(24, 'Creazione Software ', 'Salve ho bisogno di un software che mi permetta di creare un tableau', 2, '2017-07-07', '2017-07-15', 36, 1, 0),
(25, 'Task Pippo', 'Questo è il task di pippo', 4, '2017-07-07', '2017-07-15', 36, 2, 0),
(26, 'Prenotazione vacanza', 'in questo task gli sviluppatori dovranno prenotare la vacanza per gli studenti', 2, '2017-07-06', '2017-07-14', 11, 2, 0),
(30, 'Assemblamento robot', 'Bisogna montare i pezzi del robot', 4, '2017-07-07', '2018-08-08', 38, 2, 3),
(31, 'UML diagrams', 'Sviuppo della documentazione UML', 2, '2017-01-01', '2017-06-07', 38, 3, 0),
(32, 'Db dell''app "AAA"', 'Qui gli sviluppatori dovranno elaborare il db dell''applicazione.', 8, '2017-07-21', '2017-07-29', 29, 2, 0),
(33, 'Luca', 'luca', 10, '2017-01-07', '2018-02-06', 11, 1, 0),
(34, 'plugin', 'implementazione del plugin', 3, '2017-08-18', '2017-10-22', 39, 2, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `task_skill`
--

CREATE TABLE `task_skill` (
  `id` int(11) NOT NULL,
  `task` int(11) NOT NULL,
  `skill` int(11) NOT NULL,
  `livello` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `task_skill`
--

INSERT INTO `task_skill` (`id`, `task`, `skill`, `livello`) VALUES
(1, 9, 4, 2),
(7, 10, 3, 4),
(8, 10, 5, 1),
(9, 15, 3, 3),
(10, 15, 5, 4),
(11, 16, 3, 1),
(12, 18, 3, 1),
(13, 11, 4, 1),
(15, 20, 4, 1),
(16, 21, 7, 5),
(17, 13, 5, 5),
(19, 19, 4, 1),
(22, 12, 3, 1),
(23, 22, 3, 3),
(24, 22, 5, 1),
(25, 23, 3, 1),
(26, 25, 5, 1),
(27, 26, 3, 1),
(34, 12, 5, 3),
(35, 30, 5, 3),
(36, 30, 3, 1),
(37, 31, 6, 1),
(38, 32, 4, 1),
(39, 33, 4, 1),
(40, 34, 7, 4),
(41, 34, 5, 2),
(42, 34, 3, 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `tipo`
--

CREATE TABLE `tipo` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `tipo`
--

INSERT INTO `tipo` (`id`, `nome`) VALUES
(1, 'Grafica'),
(2, 'Sviluppo'),
(3, 'Documentazione');

-- --------------------------------------------------------

--
-- Struttura della tabella `tipo_skill`
--

CREATE TABLE `tipo_skill` (
  `Id` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `skill` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `tipo_skill`
--

INSERT INTO `tipo_skill` (`Id`, `tipo`, `skill`) VALUES
(2, 2, 3),
(3, 1, 4),
(4, 2, 5),
(5, 2, 7),
(6, 3, 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id` int(11) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `cognome` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` char(32) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `data_nascita` date DEFAULT NULL,
  `amministratore` varchar(1) DEFAULT 'F'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id`, `nome`, `cognome`, `email`, `password`, `telefono`, `data_nascita`, `amministratore`) VALUES
(1, 'davide', 'cognome', 'davide', 'davide', '123456789', '1212-12-12', 'F'),
(2, 'davide2', 'cognome2', 'davi95ma@hotmail.it', 'davide', '123456789', '1212-12-12', 'F'),
(3, 'Antonio', 'Pascoli', 'a@p.it', 'davide', '123', '2012-12-12', 'F'),
(4, 'Admin', 'Capo del Sito', 'admin@gmail.com', 'admin', '123456', '1995-04-15', 'T'),
(5, 'Luca', 'Grillo', 'lucag.8595@gmail.com', 'davide', '123456', '2017-07-20', 'F'),
(6, 'Tiziano', 'Santilli', 'tizianosantilli@gmail.com', 'davide', '12345', '1994-03-17', 'F'),
(7, 'Davide', 'Mariotti', 'davide@gmail.com', 'davide', '3456767655', '1995-12-08', 'F'),
(8, 'Cristian', 'Capozucco', 'c@c.it', 'davide', '123456', '1995-01-07', 'F'),
(11, 'Anna', 'Rossi', 'ar@gmail.com', 'davide', '2334', '2017-07-12', 'F'),
(12, 'Salvatore', 'Quasimodo', 'sq@gmail.com', 'davide', '234', '2017-07-26', 'F'),
(13, 'Cristian', 'Capozucco', 'cristcapo@gmail.com', 'davide', '0871333333', '2021-02-05', 'F'),
(14, 'Daniela', 'Signore', 'danielasignore@gmail.com', 'davide', 'davide', '2017-07-13', 'F'),
(15, 'marco', 'carta', 'mc@gmail.com', 'davide', '398476253', '1276-07-27', 'F'),
(16, 'alessandro', 'mariotti', 'alessandro@gmail.com', 'pippo', '3401870236', '1994-12-12', 'F');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente_progetto`
--

CREATE TABLE `utente_progetto` (
  `id` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  `progetto` int(11) NOT NULL,
  `ruolo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente_progetto`
--

INSERT INTO `utente_progetto` (`id`, `utente`, `progetto`, `ruolo`) VALUES
(6, 2, 11, 'admin'),
(7, 2, 12, 'admin'),
(23, 2, 28, 'admin'),
(24, 2, 29, 'admin'),
(25, 2, 30, 'admin'),
(26, 2, 31, 'admin'),
(27, 2, 32, 'admin'),
(28, 2, 33, 'admin'),
(30, 3, 35, 'admin'),
(31, 5, 36, 'admin'),
(32, 2, 37, 'admin'),
(33, 13, 38, 'admin'),
(34, 16, 39, 'admin');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente_skill`
--

CREATE TABLE `utente_skill` (
  `id` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  `skill` int(11) NOT NULL,
  `livello` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente_skill`
--

INSERT INTO `utente_skill` (`id`, `utente`, `skill`, `livello`) VALUES
(1, 1, 3, 2),
(2, 3, 3, 5),
(3, 4, 6, 7),
(4, 3, 4, 4),
(5, 1, 5, 3),
(18, 5, 3, 2),
(19, 5, 5, 4),
(20, 5, 3, 9),
(21, 5, 5, 7),
(22, 5, 5, 10),
(23, 2, 3, 6),
(24, 2, 4, 4),
(25, 2, 5, 4),
(26, 2, 3, 3),
(27, 6, 4, 5),
(28, 13, 3, 50);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `domanda`
--
ALTER TABLE `domanda`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente` (`utente`),
  ADD KEY `task` (`task`);

--
-- Indici per le tabelle `files`
--
ALTER TABLE `files`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `utente` (`utente`);

--
-- Indici per le tabelle `messaggio`
--
ALTER TABLE `messaggio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente` (`utente`),
  ADD KEY `progetto` (`progetto`);

--
-- Indici per le tabelle `progetto`
--
ALTER TABLE `progetto`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `proposta`
--
ALTER TABLE `proposta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente` (`utente`),
  ADD KEY `task` (`task`);

--
-- Indici per le tabelle `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `progetto` (`progetto`),
  ADD KEY `tipo` (`tipo`);

--
-- Indici per le tabelle `task_skill`
--
ALTER TABLE `task_skill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `task` (`task`),
  ADD KEY `skill` (`skill`);

--
-- Indici per le tabelle `tipo`
--
ALTER TABLE `tipo`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `tipo_skill`
--
ALTER TABLE `tipo_skill`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `tipo` (`tipo`),
  ADD KEY `skill` (`skill`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utente_progetto`
--
ALTER TABLE `utente_progetto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente` (`utente`),
  ADD KEY `progetto` (`progetto`);

--
-- Indici per le tabelle `utente_skill`
--
ALTER TABLE `utente_skill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente` (`utente`),
  ADD KEY `skill` (`skill`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `domanda`
--
ALTER TABLE `domanda`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;
--
-- AUTO_INCREMENT per la tabella `files`
--
ALTER TABLE `files`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT per la tabella `messaggio`
--
ALTER TABLE `messaggio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT per la tabella `progetto`
--
ALTER TABLE `progetto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT per la tabella `proposta`
--
ALTER TABLE `proposta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT per la tabella `skill`
--
ALTER TABLE `skill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT per la tabella `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT per la tabella `task_skill`
--
ALTER TABLE `task_skill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- AUTO_INCREMENT per la tabella `tipo`
--
ALTER TABLE `tipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT per la tabella `tipo_skill`
--
ALTER TABLE `tipo_skill`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT per la tabella `utente_progetto`
--
ALTER TABLE `utente_progetto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT per la tabella `utente_skill`
--
ALTER TABLE `utente_skill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `domanda`
--
ALTER TABLE `domanda`
  ADD CONSTRAINT `domanda_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`),
  ADD CONSTRAINT `domanda_ibfk_2` FOREIGN KEY (`task`) REFERENCES `task` (`id`);

--
-- Limiti per la tabella `files`
--
ALTER TABLE `files`
  ADD CONSTRAINT `files_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`);

--
-- Limiti per la tabella `messaggio`
--
ALTER TABLE `messaggio`
  ADD CONSTRAINT `messaggio_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`),
  ADD CONSTRAINT `messaggio_ibfk_2` FOREIGN KEY (`progetto`) REFERENCES `progetto` (`id`);

--
-- Limiti per la tabella `proposta`
--
ALTER TABLE `proposta`
  ADD CONSTRAINT `proposta_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`),
  ADD CONSTRAINT `proposta_ibfk_2` FOREIGN KEY (`task`) REFERENCES `task` (`id`);

--
-- Limiti per la tabella `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`progetto`) REFERENCES `progetto` (`id`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`tipo`) REFERENCES `tipo` (`id`);

--
-- Limiti per la tabella `task_skill`
--
ALTER TABLE `task_skill`
  ADD CONSTRAINT `task_skill_ibfk_1` FOREIGN KEY (`task`) REFERENCES `task` (`id`),
  ADD CONSTRAINT `task_skill_ibfk_2` FOREIGN KEY (`skill`) REFERENCES `skill` (`id`);

--
-- Limiti per la tabella `tipo_skill`
--
ALTER TABLE `tipo_skill`
  ADD CONSTRAINT `tipo_skill_ibfk_1` FOREIGN KEY (`tipo`) REFERENCES `tipo` (`id`),
  ADD CONSTRAINT `tipo_skill_ibfk_2` FOREIGN KEY (`skill`) REFERENCES `skill` (`id`);

--
-- Limiti per la tabella `utente_progetto`
--
ALTER TABLE `utente_progetto`
  ADD CONSTRAINT `utente_progetto_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`),
  ADD CONSTRAINT `utente_progetto_ibfk_2` FOREIGN KEY (`progetto`) REFERENCES `progetto` (`id`);

--
-- Limiti per la tabella `utente_skill`
--
ALTER TABLE `utente_skill`
  ADD CONSTRAINT `utente_skill_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`),
  ADD CONSTRAINT `utente_skill_ibfk_2` FOREIGN KEY (`skill`) REFERENCES `skill` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
