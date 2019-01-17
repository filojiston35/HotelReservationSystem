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
-- Table structure for table `event_Detail`
--

DROP TABLE IF EXISTS `event_Detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_Detail` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_title` varchar(45) NOT NULL,
  `event_message` text NOT NULL,
  `event_createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_Detail`
--

LOCK TABLES `event_Detail` WRITE;
/*!40000 ALTER TABLE `event_Detail` DISABLE KEYS */;
INSERT INTO `event_Detail` VALUES (40,'Message Title','<p><strong>adasdsadasd sa</strong></p><p><strong>das</strong></p><p><strong> dsa</strong></p><p><strong> dsa dasdasd</strong></p>','2018-12-27 01:41:16'),(41,'Message Title','<p>qwe</p>','2018-12-27 01:43:59'),(60,'Message Title','<p>a</p>','2018-12-30 01:43:53'),(61,'Message Title','<p>a</p>','2018-12-30 01:43:56'),(62,'Message Title','<p>a</p>','2018-12-30 01:43:59'),(63,'Message Title','<p>a</p>','2018-12-30 01:44:01'),(64,'Message Title','<p>a</p>','2018-12-30 01:44:03'),(65,'Message Title','<p>a</p>','2018-12-30 01:44:06'),(66,'Message Title','<p>a</p>','2018-12-30 01:44:08'),(67,'Message Title','<p>a</p>','2018-12-30 01:44:11'),(68,'Message Title','<p>a</p>','2018-12-30 01:44:13'),(69,'Message Title','<p>a</p>','2018-12-30 01:44:16'),(70,'Message Title','<p>a</p>','2018-12-30 01:44:19'),(71,'Message Title','<p>a</p>','2018-12-30 01:44:23'),(72,'Message Title','<p>a</p>','2018-12-30 01:44:27'),(73,'Message Title','<p>a</p>','2018-12-30 01:44:30'),(74,'Message Title','<p>a</p>','2018-12-30 01:44:33'),(75,'Message Title','<p>a</p>','2018-12-30 01:44:36'),(76,'Message Title','<p>a</p>','2018-12-30 01:44:39'),(77,'Message Title','<p>a</p>','2018-12-30 01:44:42'),(78,'Message Title','<p>qweqweqw</p>','2018-12-30 01:48:07'),(79,'Message Title','<p>qweqweqw</p>','2018-12-30 01:48:10'),(81,'Message Title','<h1 style=\"font-style: normal;\"><span style=\"font-weight: bold;\">Test</span></h1><div style=\"font-style: normal; font-weight: normal;\"><br></div><div style=\"font-style: normal; font-weight: normal;\"><ul><li>Test</li><li>Test</li><li>Test</li></ul></div><div style=\"font-style: normal; font-weight: normal;\"><br></div><div style=\"\"><span style=\"font-weight: bold; font-style: italic; text-decoration-line: underline;\">Test Test Test</span></div>','2018-12-30 10:18:50'),(82,'TEST','TEST bu TEST','2018-12-31 12:14:25'),(84,'TEST3','TEST 3 TEST 3','2018-12-31 12:15:40'),(85,'Message Title','<ol style=\"\"><li style=\"text-align: right;\"><span style=\"font-weight: bold; font-style: italic;\">efvgxtgbyhgdjnhbbbbbbbbbbbbbbbbbbbbbbb7578527vfffffffffffffffff////////////////////////7gbnhgcnhnhgcngm</span>c</li></ol>','2018-12-31 14:27:11'),(86,'Message Title','Çalışmaya başlayabilirsiniz. Çabuk OLUN!!!!!!!!!!!!!!!!!!11','2018-12-31 14:51:28'),(87,'Message Title','<ul><li>dasdasdas</li><li>asd</li><li>sad</li><li>sa</li></ul><div><span style=\"font-weight: bold;\"><br></span></div>','2019-01-06 14:56:49'),(88,'Message Title','asd sadsa das das dddddddddddddddddddddd<span style=\"font-size: 13.3333px;\">asd sadsa das das dddddddddddddddddddddd</span><span style=\"font-size: 13.3333px;\">asd sadsa das das dddddddddddddddddddddd</span><span style=\"font-size: 13.3333px;\">asd sadsa das das dddddddddddddddddddddd</span>','2019-01-06 14:59:23'),(89,'Message Title','<ul><li>weqq</li><li>qwe</li><li>qwe</li><li>qwe</li><li>qwe</li><li>qwe</li></ul>','2019-01-06 15:06:50');
/*!40000 ALTER TABLE `event_Detail` ENABLE KEYS */;
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
