# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.24)
# Database: chukonu-db
# Generation Time: 2019-01-25 11:43:58 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table PRODUCTS
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PRODUCTS`;

CREATE TABLE `PRODUCTS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL DEFAULT '',
  `TYPE` varchar(10) NOT NULL DEFAULT '',
  `PRICE` float NOT NULL,
  `QUOTA` int(11) DEFAULT '0',
  `UPDATE_BY` varchar(20) DEFAULT NULL,
  `UPDATE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `USERNAME_UNIQUE` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `PRODUCTS` WRITE;
/*!40000 ALTER TABLE `PRODUCTS` DISABLE KEYS */;

INSERT INTO `PRODUCTS` (`ID`, `NAME`, `TYPE`, `PRICE`, `QUOTA`, `UPDATE_BY`, `UPDATE_TIME`)
VALUES
	(1,'apple','Fruit',1,100,'admin','2019-01-25 09:01:13'),
	(2,'lemon','Car',2,99,'admin','2019-01-25 11:18:53'),
	(3,'pear','Fruit',0.99,78,'admin','2019-01-25 09:01:13'),
	(4,'avocado','Fruit',0.75,60,'admin','2019-01-25 09:01:13'),
	(5,'cantaloupe','Fruit',1.2,88,'admin','2019-01-25 09:01:13'),
	(6,'banana','Fruit',0.88,90,'admin','2019-01-25 09:01:13'),
	(7,'overcoat','Coat',100,120,'admin','2019-01-25 09:01:13'),
	(8,'coat','Coat',110,25,'admin','2019-01-25 09:01:13'),
	(9,'fur coat','Coat',89,20,'admin','2019-01-25 09:01:13'),
	(10,'dust coat','Coat',78,10,'admin','2019-01-25 09:01:13');

/*!40000 ALTER TABLE `PRODUCTS` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table USERS
# ------------------------------------------------------------

DROP TABLE IF EXISTS `USERS`;

CREATE TABLE `USERS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL DEFAULT '',
  `PASSWORD` varchar(64) NOT NULL,
  `ROLE` varchar(10) NOT NULL DEFAULT 'VIEWER',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `USERNAME_UNIQUE` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;

INSERT INTO `USERS` (`ID`, `NAME`, `PASSWORD`, `ROLE`)
VALUES
	(1,'admin','admin@123','ADMIN'),
	(2,'editor','editor@123','EDITOR'),
	(3,'viewer','viewer@123','VIEWER');

/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
