CREATE DATABASE  IF NOT EXISTS `farmsim` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `farmsim`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: farmsim
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bankaccount`
--

DROP TABLE IF EXISTS `bankaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bankaccount` (
  `player_id` int NOT NULL,
  `balance` int NOT NULL,
  `debt` int NOT NULL,
  UNIQUE KEY `player_id_UNIQUE` (`player_id`),
  KEY `bankaccount_playerinfo_idx` (`player_id`),
  CONSTRAINT `bankaccount_playerinfo` FOREIGN KEY (`player_id`) REFERENCES `playerinfo` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `growthrates`
--

DROP TABLE IF EXISTS `growthrates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `growthrates` (
  `player_id` int NOT NULL,
  `item_name` varchar(45) NOT NULL,
  `sequence` int NOT NULL,
  `stage_description` varchar(45) NOT NULL,
  `length` int NOT NULL,
  `season` varchar(45) NOT NULL,
  KEY `growthrates_playerinfo_idx` (`player_id`),
  KEY `growthrates_store_idx` (`item_name`),
  KEY `growthrates_season_idx` (`season`),
  KEY `growthrates_stage_description` (`item_name`,`stage_description`),
  CONSTRAINT `growthrates_playerinfo` FOREIGN KEY (`player_id`) REFERENCES `playerinfo` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `growthrates_season` FOREIGN KEY (`season`) REFERENCES `seasons` (`season`) ON UPDATE RESTRICT,
  CONSTRAINT `growthrates_store` FOREIGN KEY (`item_name`) REFERENCES `store` (`item_name`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `player_id` int NOT NULL,
  `item_name` varchar(45) NOT NULL,
  `quantity` int NOT NULL,
  KEY `inventory_playerinfo_idx` (`player_id`),
  KEY `inventory_store_idx` (`item_name`),
  CONSTRAINT `inventory_playerinfo` FOREIGN KEY (`player_id`) REFERENCES `playerinfo` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `inventory_store` FOREIGN KEY (`item_name`) REFERENCES `store` (`item_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `playerinfo`
--

DROP TABLE IF EXISTS `playerinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playerinfo` (
  `player_id` int NOT NULL AUTO_INCREMENT,
  `gameVersion` varchar(45) NOT NULL,
  `name` varchar(100) NOT NULL,
  `day` int NOT NULL,
  `season` varchar(45) NOT NULL,
  PRIMARY KEY (`player_id`),
  KEY `playerinfo_seasons_idx` (`season`),
  CONSTRAINT `playerinfo_seasons` FOREIGN KEY (`season`) REFERENCES `seasons` (`season`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plots`
--

DROP TABLE IF EXISTS `plots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plots` (
  `player_id` int NOT NULL,
  `plot_id` int NOT NULL,
  `tilled` bit(1) NOT NULL,
  `watered` bit(1) NOT NULL,
  `hasBigRock` bit(1) NOT NULL,
  `hasStump` bit(1) NOT NULL,
  `item_name` varchar(45) NOT NULL,
  `stage_description` varchar(45) NOT NULL,
  `daysGrown` int NOT NULL,
  KEY `plots_playerinfo_idx` (`player_id`),
  KEY `plots_growthrates_idx` (`item_name`,`stage_description`),
  CONSTRAINT `plots_growthrates` FOREIGN KEY (`item_name`, `stage_description`) REFERENCES `growthrates` (`item_name`, `stage_description`) ON UPDATE RESTRICT,
  CONSTRAINT `plots_playerinfo` FOREIGN KEY (`player_id`) REFERENCES `playerinfo` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salerates`
--

DROP TABLE IF EXISTS `salerates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salerates` (
  `player_id` int NOT NULL,
  `item_name` varchar(45) NOT NULL,
  `stage_description` varchar(45) NOT NULL,
  `profit` int NOT NULL,
  KEY `salerates_playerinfo_idx` (`player_id`),
  KEY `salerates_store_idx` (`item_name`),
  KEY `salerates_growthrates_idx` (`item_name`,`stage_description`),
  CONSTRAINT `salerates_growthrates` FOREIGN KEY (`item_name`, `stage_description`) REFERENCES `growthrates` (`item_name`, `stage_description`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `salerates_playerinfo` FOREIGN KEY (`player_id`) REFERENCES `playerinfo` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `salerates_store` FOREIGN KEY (`item_name`) REFERENCES `store` (`item_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seasons`
--

DROP TABLE IF EXISTS `seasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seasons` (
  `season` varchar(45) NOT NULL,
  `length` int NOT NULL,
  PRIMARY KEY (`season`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `item_name` varchar(45) NOT NULL,
  `player_id` int NOT NULL,
  `quantity_available` int NOT NULL,
  `cost` int NOT NULL,
  `season` varchar(45) NOT NULL,
  PRIMARY KEY (`item_name`),
  KEY `store_playerinfo_idx` (`player_id`),
  KEY `store_seasons_idx` (`season`),
  CONSTRAINT `store_playerinfo` FOREIGN KEY (`player_id`) REFERENCES `playerinfo` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `store_seasons` FOREIGN KEY (`season`) REFERENCES `seasons` (`season`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'farmsim'
--

--
-- Dumping routines for database 'farmsim'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-05 13:48:07
