-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_ticket_manage_1
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `db_ticket`
--

DROP TABLE IF EXISTS `db_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_ticket` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) NOT NULL,
  `train_id` varchar(32) NOT NULL,
  `startStation` varchar(32) NOT NULL,
  `endStation` varchar(32) NOT NULL,
  PRIMARY KEY (`ticket_id`),
  UNIQUE KEY `db_ticket_pk` (`user_id`,`train_id`),
  KEY `db_ticket_db_train_null_fk` (`train_id`),
  CONSTRAINT `db_ticket_db_train_null_fk` FOREIGN KEY (`train_id`) REFERENCES `db_train` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `db_ticket_db_user_null_fk` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_ticket`
--

LOCK TABLES `db_ticket` WRITE;
/*!40000 ALTER TABLE `db_ticket` DISABLE KEYS */;
INSERT INTO `db_ticket` VALUES (1,'shk','g234','冬瓜','南瓜');
/*!40000 ALTER TABLE `db_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_train`
--

DROP TABLE IF EXISTS `db_train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_train` (
  `tid` varchar(32) NOT NULL,
  `stations` varchar(1024) NOT NULL,
  `capacity` int NOT NULL,
  `startTime` date NOT NULL,
  `frequency` varchar(32) NOT NULL,
  `remain` int NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_train`
--

LOCK TABLES `db_train` WRITE;
/*!40000 ALTER TABLE `db_train` DISABLE KEYS */;
INSERT INTO `db_train` VALUES ('g123','北京-南京-东京-西京',3,'2023-01-17','3次/周',3),('g234','冬瓜-西瓜-南瓜-北瓜',114514,'2023-01-16','1次/周',114513);
/*!40000 ALTER TABLE `db_train` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_user`
--

DROP TABLE IF EXISTS `db_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_user` (
  `uid` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_user`
--

LOCK TABLES `db_user` WRITE;
/*!40000 ALTER TABLE `db_user` DISABLE KEYS */;
INSERT INTO `db_user` VALUES ('shk','123456',1),('test','123456',0);
/*!40000 ALTER TABLE `db_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-11 19:15:48
