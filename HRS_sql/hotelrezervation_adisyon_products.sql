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
-- Table structure for table `adisyon_products`
--

DROP TABLE IF EXISTS `adisyon_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adisyon_products` (
  `adisyon_products_id` int(11) NOT NULL AUTO_INCREMENT,
  `products_id` int(11) DEFAULT NULL,
  `adisyon_id` int(11) DEFAULT NULL,
  `purchase_date` datetime DEFAULT NULL,
  `product_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`adisyon_products_id`),
  KEY `fk_adisyon_products_1_idx` (`adisyon_id`),
  KEY `fk_adisyon_products_2_idx` (`products_id`),
  CONSTRAINT `fk_adisyon_products_1` FOREIGN KEY (`adisyon_id`) REFERENCES `adisyon` (`adisyon_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_adisyon_products_2` FOREIGN KEY (`products_id`) REFERENCES `products` (`products_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adisyon_products`
--

LOCK TABLES `adisyon_products` WRITE;
/*!40000 ALTER TABLE `adisyon_products` DISABLE KEYS */;
INSERT INTO `adisyon_products` VALUES (1,1,56,'2018-12-08 00:00:00',2),(3,3,56,'2018-12-08 00:00:00',3),(4,7,56,'2018-12-12 06:06:27',1),(5,6,56,'2018-12-12 06:09:55',5),(6,3,56,'2018-12-12 06:09:55',5),(7,2,56,'2018-12-12 06:12:14',2),(8,5,56,'2018-12-12 06:15:47',2),(9,12,56,'2018-12-12 06:15:47',21),(10,8,56,'2018-12-12 06:20:19',2),(11,1,56,'2018-12-12 06:21:19',2),(12,5,56,'2018-12-12 06:21:19',11),(14,8,57,'2018-12-12 06:32:01',2),(17,6,60,'2018-12-12 07:16:01',3),(21,1,60,'2018-12-12 07:29:35',1),(24,6,60,'2018-12-12 07:52:52',4),(25,6,60,'2018-12-12 07:52:52',10),(32,7,57,'2018-12-12 09:24:12',3),(34,6,57,'2018-12-12 09:24:12',1),(35,6,57,'2018-12-12 09:24:12',2),(36,6,57,'2018-12-12 09:24:12',3),(40,2,57,'2018-12-12 09:27:40',2),(42,2,57,'2018-12-12 09:27:40',1),(44,8,60,'2018-12-12 19:42:10',1),(46,5,60,'2018-12-15 15:46:53',2),(48,7,63,'2018-12-15 15:53:33',1),(49,8,66,'2018-12-17 11:45:57',1),(50,11,65,'2018-12-23 18:56:24',5),(51,11,65,'2018-12-23 18:56:24',4),(52,7,65,'2018-12-23 19:23:36',1),(53,7,65,'2018-12-23 19:23:36',2),(54,8,65,'2018-12-23 19:23:36',1),(55,11,65,'2018-12-23 19:23:36',1),(56,6,64,'2018-12-23 19:27:18',1),(57,3,61,'2018-12-25 22:25:25',5),(58,7,79,'2018-12-28 13:58:54',2),(59,12,79,'2018-12-28 13:58:54',2),(60,12,79,'2018-12-28 13:58:54',1),(61,12,79,'2018-12-28 13:58:54',8),(64,11,63,'2018-12-28 14:13:57',3),(66,6,80,'2018-12-28 14:46:21',1),(67,6,80,'2018-12-28 14:50:45',1),(68,6,80,'2018-12-28 14:50:45',3),(69,8,80,'2018-12-28 14:50:45',4),(73,6,84,'2019-01-06 18:37:35',2),(74,7,84,'2019-01-06 18:37:39',1),(75,8,84,'2019-01-06 18:37:46',1),(76,4,84,'2019-01-06 18:38:05',1),(77,5,84,'2019-01-06 18:38:14',1),(78,3,88,'2019-01-07 15:28:06',5),(79,3,85,'2019-01-09 11:59:24',2),(80,8,85,'2019-01-09 11:59:27',1),(81,4,85,'2019-01-09 11:59:30',3),(82,2,85,'2019-01-09 11:59:36',5);
/*!40000 ALTER TABLE `adisyon_products` ENABLE KEYS */;
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
