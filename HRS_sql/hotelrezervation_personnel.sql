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
-- Table structure for table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personnel` (
  `personnel_id` int(11) NOT NULL AUTO_INCREMENT,
  `personnel_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `personnel_surname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `personnel_age` int(11) DEFAULT NULL,
  `personnel_department` int(11) NOT NULL,
  `personnel_tc` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `personnel_admin` tinyint(1) NOT NULL,
  `personnel_mail` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `personnel_password` varchar(60) CHARACTER SET utf8 DEFAULT NULL,
  `personnel_phone` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `personnel_image` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `personnel_reservation_authority` tinyint(1) DEFAULT NULL,
  `personnel_addition_authority` tinyint(1) DEFAULT NULL,
  `personnel_managment_auhority` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`personnel_id`),
  KEY `fk_personnel_1_idx` (`personnel_department`),
  CONSTRAINT `fk_personnel_1` FOREIGN KEY (`personnel_department`) REFERENCES `departments` (`departments_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personnel`
--

LOCK TABLES `personnel` WRITE;
/*!40000 ALTER TABLE `personnel` DISABLE KEYS */;
INSERT INTO `personnel` VALUES (1,'yusuf','unlu',22,4,'23432424242',1,'myunlu1@gmail.com','f8clcle9a6c2c4b9dddiae8rebaubvfb','+90 (541) 431-8216','download.jpeg',1,1,1),(11,'cevher','yılmaz',23,15,'23432424242',0,'myunlu2@gmail.com','f8clcle9a6c2c4b9dddiae8rebaubvfb','','user-alt-solid.svg',0,0,0),(30,'ALPARSLAN','ÇAY',12,3,'12321312321',0,'myunlu3@gmail.com','crc8f9ao8o9q8hciejbsbfebd2fhb2fc','+90 (123) 213-1232','user-alt-solid.svg',0,0,0),(31,'KADİR','ARI',32,24,'12312312312',0,'myunlu4@gmail.com','8v8ue6cgc0d7alal96e29t938o938ibo','+13 (213) 213-2132','user-alt-solid.svg',0,0,0),(32,'ORKUN','İNAN',32,24,'12321312321',0,'myunlu5@gmail.com','c78fd895dacre98pcefabt8bc7dtcs9t','+32 (132) 132-1321','user-alt-solid.svg',0,0,0),(33,'ZEYNEP','ER',32,1,'12312321321',0,'myunlu6@gmail.com','b68m8gbnbna7a7emdqf6f0dj918dbval','+32 (312) 321-3131','user-alt-solid.svg',0,0,0),(34,'ELA','ER',21,4,'12321312321',0,'myunlu7@gmail.com','cge59ker9bfjbhd1dd81a7bvb0b0epcg','+12 (321) 321-3123','user-alt-solid.svg',0,0,0),(35,'AYÇA','ÇELEBİ',23,2,'12312312321',0,'myunlu8@gmail.com','cebu809oefbaflavafcm9cfdai9ga69v','+32 (132) 132-1321','user-alt-solid.svg',0,0,0),(36,'MELİS','ERTANOĞULLARI',23,3,'32132132132',0,'myunlu9@gmail.com','adf0aofqcjcjfc8t97fqb3bca2d4ere1','+23 (213) 123-1232','user-alt-solid.svg',0,0,0),(37,'MİRA','ÖZDOĞANLAR',21,34,'32312321312',0,'myunlu10@gmail.com','acc590afd988dp95etbrehancqbie7c2','+90 (213) 123-2131','user-alt-solid.svg',0,0,0),(38,'arif','tutar',21,24,'21312312321',0,'myunlu11@gmail.com','dnfib4en8nbi8hdoc8bhelamed93e9dg','+23 (123) 123-1231','user-alt-solid.svg',0,0,0),(39,'ahmet','mehmet',21,24,'32321321312',0,'myunlu12@gmail.com','df978d868aeue9ec9earee8oaea8d7a5','+32 (132) 131-2321','user-alt-solid.svg',0,0,0),(40,'mahmut','tuncer',23,24,'32131231232',0,'myunlu13@gmail.com','am8vau9c95dha7caeudm9napbt8kb49f','+43 (243) 243-2423','user-alt-solid.svg',0,0,0);
/*!40000 ALTER TABLE `personnel` ENABLE KEYS */;
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
