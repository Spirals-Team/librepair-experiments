-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 18-06-2018 a las 15:46:46
-- Versión del servidor: 5.7.19
-- Versión de PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tp5`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `airports`
--

DROP TABLE IF EXISTS `airports`;
CREATE TABLE IF NOT EXISTS `airports` (
  `id_airport` bigint(20) NOT NULL,
  `iata` varchar(255) DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `fk_id_city` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_airport`),
  KEY `FKe3w5voc71ljxt3o3isgt2vr5n` (`fk_id_city`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `airports`
--

INSERT INTO `airports` (`id_airport`, `iata`, `latitude`, `longitude`, `name`, `fk_id_city`) VALUES
(14, 'IBZ', 38.52, 1.22, 'Aeropuerto de Ibiza', 10),
(15, 'SCL', 33.23, 70.47, 'Aeropuerto Internacional Comodoro Benitez', 8),
(16, 'MAD', 190.28, 37878.3, 'Aeropuerto de Madrid-Barajas', 9),
(118, 'MDQ', 3335.38, 487.54, 'Aeropuerto Internacional Astor Piazolla', 117),
(119, 'CWB', 999.3, 663.3, 'Aeropuerto Internacional Afonso Pena\r\n', 118),
(121, 'EZE', 500.7, 663.875, 'Aeropuerto Internacional Ministro Pistarini', 120);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cabins`
--

DROP TABLE IF EXISTS `cabins`;
CREATE TABLE IF NOT EXISTS `cabins` (
  `id_cabin` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cabin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cabins`
--

INSERT INTO `cabins` (`id_cabin`, `name`) VALUES
(1, 'Economica'),
(2, 'Plus'),
(3, 'Business'),
(4, 'Primera');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cities`
--

DROP TABLE IF EXISTS `cities`;
CREATE TABLE IF NOT EXISTS `cities` (
  `id_city` bigint(20) NOT NULL,
  `iata` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `fk_id_country` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_city`),
  KEY `FK2q7krjp07a1vgt5icciv2pgoy` (`fk_id_country`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cities`
--

INSERT INTO `cities` (`id_city`, `iata`, `name`, `fk_id_country`) VALUES
(8, 'SCL', 'Santiago de Chile', 5),
(9, 'MAD', 'Madrid', 3),
(10, 'IBZ', 'Ibiza', 3),
(117, 'MDQ', 'Mar del Plata', 116),
(118, 'SJP', 'São José dos Pinhais', 117),
(120, 'BA', 'Buenos Aires', 116);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `countries`
--

DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (
  `id_country` bigint(20) NOT NULL,
  `iso` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_country`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `countries`
--

INSERT INTO `countries` (`id_country`, `iso`, `name`) VALUES
(3, 'ES', 'España'),
(5, 'CL', 'Chile'),
(116, 'ARG', 'Argentina'),
(117, 'BRA', 'Brasil');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1),
(1),
(1),
(1),
(1),
(1),
(1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prices`
--

DROP TABLE IF EXISTS `prices`;
CREATE TABLE IF NOT EXISTS `prices` (
  `id_price` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `fk_id_route` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_price`),
  KEY `FK36n8ncklcihcphwn1w38j4pyc` (`fk_id_route`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `prices`
--

INSERT INTO `prices` (`id_price`, `date`, `price`, `fk_id_route`) VALUES
(1, '2018-06-01 00:00:00', 2000, 1),
(2, '2018-06-07 00:00:00', 2500, 1),
(3, '2018-06-06 00:00:00', 5000, 2),
(4, '2018-06-22 00:00:00', 3000, 4),
(5, '2018-06-01 00:00:00', 1500, 4),
(6, '2018-06-18 00:00:00', 3000, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `routes`
--

DROP TABLE IF EXISTS `routes`;
CREATE TABLE IF NOT EXISTS `routes` (
  `id` bigint(20) NOT NULL,
  `distance` float DEFAULT NULL,
  `destination` bigint(20) DEFAULT NULL,
  `origin` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8x310a69na3wsipkw7ij62yha` (`destination`),
  KEY `FK2lx8v5odphomt78itpebc6pks` (`origin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `routes`
--

INSERT INTO `routes` (`id`, `distance`, `destination`, `origin`) VALUES
(1, 3587, 14, 16),
(2, 3333, 16, 15),
(122, 600, 121, 118);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `routexcabin`
--

DROP TABLE IF EXISTS `routexcabin`;
CREATE TABLE IF NOT EXISTS `routexcabin` (
  `id` bigint(20) NOT NULL,
  `cabin_id_cabin` bigint(20) DEFAULT NULL,
  `route_id_route` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrp6adhiuqo62c96jeiewtpjrp` (`cabin_id_cabin`),
  KEY `FKbqjqhgraml448savpog2f5bir` (`route_id_route`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `routexcabin`
--

INSERT INTO `routexcabin` (`id`, `cabin_id_cabin`, `route_id_route`) VALUES
(1, 3, 1),
(2, 1, 1),
(3, 4, 122),
(4, 2, 122);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `airports`
--
ALTER TABLE `airports`
  ADD CONSTRAINT `FKe3w5voc71ljxt3o3isgt2vr5n` FOREIGN KEY (`fk_id_city`) REFERENCES `cities` (`id_city`);

--
-- Filtros para la tabla `cities`
--
ALTER TABLE `cities`
  ADD CONSTRAINT `FK2q7krjp07a1vgt5icciv2pgoy` FOREIGN KEY (`fk_id_country`) REFERENCES `countries` (`id_country`);

--
-- Filtros para la tabla `prices`
--
ALTER TABLE `prices`
  ADD CONSTRAINT `FK36n8ncklcihcphwn1w38j4pyc` FOREIGN KEY (`fk_id_route`) REFERENCES `routexcabin` (`id`);

--
-- Filtros para la tabla `routes`
--
ALTER TABLE `routes`
  ADD CONSTRAINT `FK2lx8v5odphomt78itpebc6pks` FOREIGN KEY (`origin`) REFERENCES `airports` (`id_airport`),
  ADD CONSTRAINT `FK8x310a69na3wsipkw7ij62yha` FOREIGN KEY (`destination`) REFERENCES `airports` (`id_airport`);

--
-- Filtros para la tabla `routexcabin`
--
ALTER TABLE `routexcabin`
  ADD CONSTRAINT `FKbqjqhgraml448savpog2f5bir` FOREIGN KEY (`route_id_route`) REFERENCES `routes` (`id`),
  ADD CONSTRAINT `FKrp6adhiuqo62c96jeiewtpjrp` FOREIGN KEY (`cabin_id_cabin`) REFERENCES `cabins` (`id_cabin`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
