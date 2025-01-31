

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `adresse` text COLLATE utf8mb4_general_ci NOT NULL,
  `mail` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `birth_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`id`, `firstname`, `name`, `phone`, `adresse`, `mail`, `birth_date`) VALUES
(9, 'Jean', 'bite', '0612110320', 'route de l\'anus', 'bobo@toto.fr', '2001-12-13'),
(11, 'Francis', 'Couscous', '0645784589', '56 avenue de la testicule', 'FrancisCouscous@Couscous.fr', '2003-12-24');


DROP TABLE IF EXISTS `exercices`;
CREATE TABLE IF NOT EXISTS `exercices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `time` double NOT NULL,
  `repetitions` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `exercices` (`id`, `name`, `time`, `repetitions`) VALUES
(11, 'dips', 10, 13),
(12, 'pompe diamants', 15, 10);

DROP TABLE IF EXISTS `programs`;
CREATE TABLE IF NOT EXISTS `programs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num_program` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `time` double NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `programs` (`id`, `num_program`, `status`, `time`, `client_id`) VALUES
(1, 275, 1, 12500, 1),
(2, 358, 0, 25000, 1),
(3, 897, 0, 7768691207, 6),
(4, 555, 0, 20, 6),
(5, 180, 0, 20, 6),
(6, 696, 0, 20, 6),
(7, 906, 0, 20, 6),
(8, 321, 0, 80, 3),
(9, 629, 0, 12500, 3);



DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`id`, `username`, `password`, `created_at`) VALUES
(2, 'safi', '$2a$10$MuruCN.u7tSI1NxaM0DFeuB4ruyz6uGwIAKA1tmW.VQDzmdadABOy', '2025-01-17 11:10:34');
COMMIT;
coachifyusersusers