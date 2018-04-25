
CREATE TABLE `node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `comments` LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;



CREATE TABLE `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `node_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgjiwu77jcgsyw3b21xu69d72x` (`node_id`),
  CONSTRAINT `FKgjiwu77jcgsyw3b21xu69d72x` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `interview` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` LONGTEXT DEFAULT NULL,
  `date` date DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn2fk6g8eem4m48trru0r8rcji` (`company_id`),
  KEY `FK4isknufpp0gkgy33qppcmgk32` (`student_id`),
  CONSTRAINT `FK4isknufpp0gkgy33qppcmgk32` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKn2fk6g8eem4m48trru0r8rcji` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB;

