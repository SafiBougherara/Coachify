-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           8.0.30 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Listage de la structure de table coachify. clients
CREATE TABLE IF NOT EXISTS `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `adresse` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `birth_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Listage des données de la table coachify.clients : ~3 rows (environ)
INSERT INTO `clients` (`id`, `firstname`, `name`, `phone`, `adresse`, `mail`, `birth_date`) VALUES
	(9, 'Jean', 'bite', '0612110320', 'route de l\'anus', 'bobo@toto.fr', '2001-12-13'),
	(11, 'Francis', 'Couscous', '0645784589', '56 avenue de la testicule', 'FrancisCouscous@Couscous.fr', '2003-12-24'),
	(12, 'Sonny', 'Verra', '0611225544', '98 route de la queue', 'grosbg@bg.fr', '2003-01-03');

-- Listage de la structure de table coachify. exercices
CREATE TABLE IF NOT EXISTS `exercices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time` double NOT NULL,
  `repetitions` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Listage des données de la table coachify.exercices : ~4 rows (environ)
INSERT INTO `exercices` (`id`, `name`, `time`, `repetitions`) VALUES
	(11, 'dips', 10, 13),
	(12, 'pompe diamants', 15, 10),
	(13, 'sucer un orteil', 250, 999),
	(14, 'manger de la merde ', 11, 20);

-- Listage de la structure de table coachify. exo_prog
CREATE TABLE IF NOT EXISTS `exo_prog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exo_id` int DEFAULT NULL,
  `prog_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_exo_id_to_id_exercices` (`exo_id`),
  KEY `FK_prog_id_to_id_programs` (`prog_id`),
  CONSTRAINT `FK_exo_id_to_id_exercices` FOREIGN KEY (`exo_id`) REFERENCES `exercices` (`id`),
  CONSTRAINT `FK_prog_id_to_id_programs` FOREIGN KEY (`prog_id`) REFERENCES `programs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table coachify.exo_prog : ~2 rows (environ)
INSERT INTO `exo_prog` (`id`, `exo_id`, `prog_id`) VALUES
	(1, 11, 1),
	(2, 11, 1);

-- Listage de la structure de table coachify. programs
CREATE TABLE IF NOT EXISTS `programs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num_program` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `time` double NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Listage des données de la table coachify.programs : ~14 rows (environ)
INSERT INTO `programs` (`id`, `num_program`, `status`, `time`, `client_id`) VALUES
	(1, 275, 1, 12500, 1),
	(2, 358, 0, 25000, 1),
	(3, 897, 0, 7768691207, 6),
	(4, 555, 0, 20, 6),
	(5, 180, 0, 20, 6),
	(6, 696, 0, 20, 6),
	(7, 906, 0, 20, 6),
	(8, 321, 0, 80, 3),
	(9, 629, 0, 12500, 3),
	(10, 270, 0, 20, 9),
	(11, 697, 0, 60, 11),
	(13, 230, 0, 60, 11),
	(14, 548, 0, 775, 11),
	(18, 684, 0, 22, 12);

-- Listage de la structure de table coachify. users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table coachify.users : 2 rows
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`, `password`, `created_at`) VALUES
	(2, 'safi', '$2a$10$MuruCN.u7tSI1NxaM0DFeuB4ruyz6uGwIAKA1tmW.VQDzmdadABOy', '2025-01-17 11:10:34'),
	(4, 'Sonny', '$2a$10$kyGB.bvPt6rQaDIlmCvk2ujzw/daOgdaul672cooCaFHPs.kqzAwu', '2025-01-31 07:41:28');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
