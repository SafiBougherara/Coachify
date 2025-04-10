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
);

-- Listage de la structure de table coachify. exercices
CREATE TABLE IF NOT EXISTS `exercices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time` double NOT NULL,
  `repetitions` int NOT NULL,
  PRIMARY KEY (`id`)
);

-- Listage de la structure de table coachify. exo_prog
CREATE TABLE IF NOT EXISTS `exo_prog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exo_id` int DEFAULT NULL,
  `prog_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_exo_id_to_id_exercices` (`exo_id`),
  KEY `FK_prog_id_to_id_programs` (`prog_id`),
  CONSTRAINT `FK_exo_id_to_id_exercices` FOREIGN KEY (`exo_id`) REFERENCES `exercices` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_prog_id_to_id_programs` FOREIGN KEY (`prog_id`) REFERENCES `programs` (`id`) ON DELETE CASCADE
);

-- Listage de la structure de table coachify. programs
CREATE TABLE IF NOT EXISTS `programs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num_program` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `time` double NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `fk_client_id_to_id_clients` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE CASCADE
);

-- Listage de la structure de table coachify. users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);