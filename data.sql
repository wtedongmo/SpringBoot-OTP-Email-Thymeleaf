
-- Dumping database structure for test
DROP DATABASE IF EXISTS `testotp`;
CREATE DATABASE IF NOT EXISTS `testotp`

-- Dumping structure for table test.book
DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g0286ag1dlt4473st1ugemd0m` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table test.book: ~0 rows (approximately)
DELETE FROM `book`;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping structure for table test.user
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(255) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table test.user: ~2 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `users` (`username`, `country`, `full_name`, `password`, `role`) VALUES
	('admin', 'Cmr', 'Admin -Wilfried', '$2a$10$Z5F2Elzpnwx6kp0CYLmdo.Tcv8SZWMANvlr/PWr6.IxWWXnAi7KNC', 'ROLE_ADMIN'),
	('user', 'Cmr', 'User-Wilf', '$2a$10$KFsPE4H9buyijUht5nQU..qdpkDRA5q6zPeACtLVWAaDh3kMzPxXG', 'ROLE_USER');
	
	/*
	password and user name
	admin/admin
	user/user
	*/
