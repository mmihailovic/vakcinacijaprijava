-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: baza
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `opstina`
--

DROP TABLE IF EXISTS `opstina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opstina` (
  `idOpstina` int NOT NULL,
  `Pttbroj` int NOT NULL,
  `Naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idOpstina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opstina`
--

LOCK TABLES `opstina` WRITE;
/*!40000 ALTER TABLE `opstina` DISABLE KEYS */;
INSERT INTO `opstina` VALUES (1,11030,'Cukarica'),(2,11060,'Palilula'),(3,11070,'Novi Beograd'),(4,14000,'Cajetina');
/*!40000 ALTER TABLE `opstina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prijava`
--

DROP TABLE IF EXISTS `prijava`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prijava` (
  `idPrijava` int NOT NULL AUTO_INCREMENT,
  `Drzavljanstvo` varchar(45) NOT NULL,
  `JMBG` varchar(45) NOT NULL,
  `Ime` varchar(45) NOT NULL,
  `Prezime` varchar(45) NOT NULL,
  `ElektronskaPosta` varchar(45) NOT NULL,
  `Mobilnitelefon` varchar(45) NOT NULL,
  `SpecificnaOboljenja` varchar(45) NOT NULL,
  `DatumPrijave` datetime(6) NOT NULL,
  `OpstinaID` int NOT NULL,
  PRIMARY KEY (`idPrijava`),
  KEY `OpstinaID_idx` (`OpstinaID`),
  CONSTRAINT `idOpstina` FOREIGN KEY (`OpstinaID`) REFERENCES `opstina` (`idOpstina`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prijava`
--

LOCK TABLES `prijava` WRITE;
/*!40000 ALTER TABLE `prijava` DISABLE KEYS */;
INSERT INTO `prijava` VALUES (6,'Drzavljanstvo Republike Srbije','394839284','asjdisajdij','asdijasid','jasidjiasdjij','93489384298','Ne','2021-08-11 16:59:04.952939',3),(8,'Strani drzavljanin bez boravka u RS','39412834982','asdijsiajdij','aisdjiasjdij','aisjdisajdjisda','958493485','Ne','2021-08-11 18:35:56.358857',1),(10,'Drzavljanstvo Republike Srbije','230985450498','phokyophytkhyopt','hjkokjohyokphy','kohgokgthokhg','923484932','Da','2021-08-11 18:38:35.784882',2),(11,'Drzavljanstvo Republike Srbije','5738743587','jasdijsdiaj','aisjdiasjd','aisjdiasjd','9328439','Da','2021-08-12 00:59:51.429510',4),(12,'Drzavljanstvo Republike Srbije','561165156','aidsjisadjij','asdijsaijdis','jdsaijdsaijd','2398498234','Ne','2021-08-12 01:00:10.710863',2),(13,'Drzavljanstvo Republike Srbije','30928429384','uqsdjsaudjj','asduhuashduash','usadhuashdsauhd','943284832','Da','2021-08-12 01:00:26.384092',2),(16,'Drzavljanstvo Republike Srbije','8534758473','aisjdisjd','isajdisaj','iasjdiasjd','349289842','Da','2021-08-12 02:25:50.687948',1),(24,'Strani drzavljanin bez boravka u RS','4832747832','suahdusahd','uashduashd','aushdusahd','34928342','Da','2021-08-13 02:02:51.566206',2),(25,'Drzavljanstvo Republike Srbije','842374782','dushaudsha','uashduahds','asudhsuahd','283742','Da','2021-08-13 02:05:13.870249',2),(26,'Drzavljanstvo Republike Srbije','8472374','sajidjaisdj','iasjdijasd','isjaisdaj','923482','Da','2021-08-13 02:06:46.034689',3),(28,'Drzavljanstvo Republike Srbije','9281474412922','Markooooo','Mihailoviccccccccc','marko@yahoo.com','0628152893','Ne','2021-08-19 03:58:32.487601',1),(29,'Drzavljanstvo Republike Srbije','8324782348733','Zooooooooki','Mihailoviiiiiiiic','zoki@yahoo.com','934834928','Ne','2021-08-19 04:05:39.245612',1),(30,'Strani drzavljanin sa boravkom u RS','8547354738574','Kacaaaaaaa','Mihailooviiic','kaca@gmail.com','9843483484','Ne','2021-08-19 04:09:19.536417',3),(31,'Drzavljanstvo Republike Srbije','3424343434343','Makiiiiiiii','Mihailoviiiic','maki@yahoo.com','3443434343','Ne','2021-08-19 04:11:38.607080',3);
/*!40000 ALTER TABLE `prijava` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavkaprijave`
--

DROP TABLE IF EXISTS `stavkaprijave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavkaprijave` (
  `PrijavaID` int NOT NULL,
  `RB` int NOT NULL,
  `TipVakcine` varchar(45) NOT NULL,
  PRIMARY KEY (`PrijavaID`,`RB`),
  CONSTRAINT `PrijavaID` FOREIGN KEY (`PrijavaID`) REFERENCES `prijava` (`idPrijava`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavkaprijave`
--

LOCK TABLES `stavkaprijave` WRITE;
/*!40000 ALTER TABLE `stavkaprijave` DISABLE KEYS */;
INSERT INTO `stavkaprijave` VALUES (6,1,'Pfizer'),(6,2,'Sputnik'),(6,3,'Sinopharm'),(6,4,'Moderna'),(8,1,'Moderna'),(10,1,'Sputnik'),(11,1,'Sputnik'),(12,1,'Moderna'),(13,1,'Sinopharm'),(16,1,'Sinopharm'),(16,2,'Moderna'),(24,1,'Pfizer'),(24,2,'Sputnik'),(24,3,'Sinopharm'),(25,1,'Pfizer'),(25,2,'Sputnik'),(25,3,'Sinopharm'),(26,1,'Pfizer'),(26,2,'Sputnik'),(26,3,'Sinopharm'),(28,1,'Sputnik'),(29,1,'Pfizer'),(30,1,'Moderna'),(31,1,'Sinopharm');
/*!40000 ALTER TABLE `stavkaprijave` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-19 17:06:14
