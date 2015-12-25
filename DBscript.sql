-- phpMyAdmin SQL Dump
-- version 4.0.4.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 22, 2015 at 04:29 PM
-- Server version: 5.6.13
-- PHP Version: 5.4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `vivesgoal`
--
CREATE DATABASE IF NOT EXISTS `vivesgoal` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `vivesgoal`;

-- --------------------------------------------------------

--
-- Table structure for table `persoon`
--

CREATE TABLE IF NOT EXISTS `persoon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naam` varchar(32) NOT NULL,
  `voornaam` varchar(32) NOT NULL,
  `geboortedatum` date NOT NULL,
  `opmerking` text,
  `isTrainer` tinyint(1) NOT NULL,
  `ploeg_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `is speler van` (`ploeg_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=105 ;

--
-- Dumping data for table `persoon`
--

INSERT INTO `persoon` (`id`, `naam`, `voornaam`, `geboortedatum`, `opmerking`, `isTrainer`, `ploeg_id`) VALUES
(1, 'Claeys', 'David', '1995-08-14', 'Goede trainer, niets op aan te merken', 1, NULL),
(2, 'Marke', 'Ronnie', '1994-08-14', NULL, 1, NULL),
(3, 'Claeys', 'Mamina', '1993-08-14', NULL, 0, 1),
(4, 'Concita', 'Davida', '1992-08-14', NULL, 0, 1),
(5, 'Claeys', 'Christina', '1991-08-14', NULL, 0, 2),
(6, 'Descheemaecker', 'Ruben', '1990-08-14', 'Zeer goede en aandachte speler.', 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `ploeg`
--

CREATE TABLE IF NOT EXISTS `ploeg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naam` varchar(32) NOT NULL,
  `niveau` enum('U6','U7','U8','U9','U10','U11') NOT NULL,
  `trainer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `heeft als trainer` (`trainer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=162 ;

--
-- Dumping data for table `ploeg`
--

INSERT INTO `ploeg` (`id`, `naam`, `niveau`, `trainer_id`) VALUES
(1, 'U8a', 'U8', 1),
(2, 'U8b', 'U8', 1),
(3, 'U10a', 'U10', 2),
(4, 'U11a', 'U11', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `persoon`
--
ALTER TABLE `persoon`
  ADD CONSTRAINT `is speler van` FOREIGN KEY (`ploeg_id`) REFERENCES `ploeg` (`id`);

--
-- Constraints for table `ploeg`
--
ALTER TABLE `ploeg`
  ADD CONSTRAINT `heeft als trainer` FOREIGN KEY (`trainer_id`) REFERENCES `persoon` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
