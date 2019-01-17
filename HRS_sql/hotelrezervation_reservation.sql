-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: hotelrezervation
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_createDate` datetime NOT NULL,
  `reservation_startDate` datetime NOT NULL,
  `reservation_endDate` datetime NOT NULL,
  `reservation_name` varchar(45) DEFAULT NULL,
  `reservation_comment` text,
  `reservation_hasBaby` tinyint(1) unsigned DEFAULT NULL,
  `reservation_peopleCount` int(11) DEFAULT '1',
  `room_id` int(11) NOT NULL,
  `reservation_mail` varchar(60) DEFAULT NULL,
  `reservation_phone` varchar(45) DEFAULT NULL,
  `reservation_price` float DEFAULT NULL,
  `reservation_hasPrice` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`reservation_id`),
  KEY `fk_reservation_1_idx` (`room_id`),
  CONSTRAINT `fk_reservation_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (56,'2019-01-03 01:25:26','2019-01-05 00:00:00','2019-01-06 00:00:00','DENEME2','',0,1,4,'deneme2@gmial.com','(123) 123-2132',200.65,0),(57,'2018-12-10 01:27:12','2019-01-05 00:00:00','2019-01-06 00:00:00','DENEME3','',0,1,5,'qasdsadsa@gmail.com','(123) 213-1231',200.65,0),(60,'2018-12-10 16:23:11','2018-12-10 00:00:00','2018-12-15 00:00:00','sdfsd','',0,1,2,'sdfsd@gmail.com','(123) 123-1231',200.65,0),(61,'2018-12-12 09:50:28','2019-01-03 00:00:00','2019-01-05 00:00:00','YUSUF','Test açıklaması.',0,1,6,'qwewq@gmail.com','(123) 123-1231',200.65,0),(62,'2018-12-12 19:38:42','2018-12-12 00:00:00','2018-12-13 00:00:00','asdsad','',0,1,1,'asdasd@gmail.com','(213) 213-1231',200.65,0),(63,'2018-12-13 07:20:50','2018-12-13 00:00:00','2018-12-15 00:00:00','today','',0,1,3,'today@gmail.com','',200.65,0),(64,'2018-12-13 08:55:10','2018-12-18 00:00:00','2018-12-20 00:00:00','Tomorrow','\"DELETE FROM Reservation  WHERE reservation_id= :56\";',0,1,4,'asdasd@gmail.com','(123) 123-1212',200.65,0),(65,'2018-12-15 15:10:18','2018-12-20 00:00:00','2018-12-21 00:00:00','Aykutqqqqq','',0,1,3,'asdasd@gmail.com','',200.65,0),(66,'2018-12-17 11:42:55','2018-12-17 00:00:00','2018-12-18 00:00:00','sergen','',1,1,3,'sergenwqe@gmail.com','(123) 231-2312',200.65,0),(79,'2018-12-25 22:25:01','2018-12-25 00:00:00','2018-12-28 00:00:00','denem','',1,1,3,'asdasdas@gmail.com','+12 (321) 312-3213',200.65,0),(80,'2018-12-28 10:41:04','2018-12-28 00:00:00','2018-12-29 00:00:00','qwqweqweqweqw','',0,1,3,'asdasdsa@gmail.com','+12 (312) 321-3213',200.65,0),(81,'2018-12-28 14:16:21','2018-12-28 00:00:00','2018-12-29 00:00:00','Muhammet','eerrrr',0,1,4,'muhammetsenturk@mail.com','+90 (549) 397-2311',200.65,0),(84,'2019-01-06 18:17:59','2019-01-06 00:00:00','2019-01-09 00:00:00','AHMET BEY VE EŞİ','',0,1,3,'asdasdsa@gmail.com','+12 (312) 321-3213',500,1),(85,'2019-01-06 18:19:55','2019-01-06 00:00:00','2019-01-10 00:00:00','NESRİN HANIM VE AİLESİ','<span style=\"font-weight: bold;\">Nesrin hanımın bir adet bebeği var.</span><div style=\"\"><span style=\"font-weight: bold;\">Oda düzenini ona göre değiştirelim.</span></div>',1,2,6,'asdasdsa@gmail.com','+12 (312) 321-3213',1000,0),(86,'2019-01-06 18:24:27','2019-01-06 00:00:00','2019-01-10 00:00:00','CEVHER VE KIZ ARKADAŞI','<span style=\"font-weight: bold;\">Cevher beyi rahatsız etmeyelim.Yurtdışından geliyorlar.</span><h3 style=\"\"><span style=\"font-weight: bold;\">Odalarına bir adet kırmızı şarap istendi !</span></h3>',0,2,1,'asdasdsa@gmail.com','+12 (312) 321-3213',800,0),(87,'2019-01-06 18:26:24','2019-01-06 00:00:00','2019-01-10 00:00:00','AYTEN HANIM','',0,1,2,'asdasdsa@gmail.com','+12 (312) 321-3213',990,0),(88,'2019-01-06 18:27:08','2019-01-07 00:00:00','2019-01-09 00:00:00','TALHA BEY','',0,1,5,'asdasdsa@gmail.com','+12 (312) 321-3213',500,0),(89,'2019-01-06 18:27:50','2019-01-06 00:00:00','2019-01-07 00:00:00','VALERIE','',0,1,4,'asdasdsa@gmail.com','+12 (312) 321-3213',400,0);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-17 23:23:36
