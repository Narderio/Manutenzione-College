-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: manutenzione
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `chat`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_utente_due` varchar(255) DEFAULT NULL,
  `id_utente_uno` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrwxrnxvhgs6x7ys741mwxyqi3` (`id_utente_uno`,`id_utente_due`),
  KEY `FKps39fkudewjyj7qfopm61fios` (`id_utente_due`),
  CONSTRAINT `FK9yh0q22a73ssukiti8efpsgri` FOREIGN KEY (`id_utente_uno`) REFERENCES `utente` (`email`),
  CONSTRAINT `FKps39fkudewjyj7qfopm61fios` FOREIGN KEY (`id_utente_due`) REFERENCES `utente` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luogo`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `luogo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capienza` int NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `nucleo` varchar(255) DEFAULT NULL,
  `piano` int NOT NULL,
  `tipo` enum('Bagno','LuogoComune','Stanza') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luogo`
--

LOCK TABLES `luogo` WRITE;
/*!40000 ALTER TABLE `luogo` DISABLE KEYS */;
/*!40000 ALTER TABLE `luogo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manutenzione`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manutenzione` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_prevista` date DEFAULT NULL,
  `data_richiesta` date DEFAULT NULL,
  `data_riparazione` date DEFAULT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `immagine` longtext,
  `nome` varchar(255) DEFAULT NULL,
  `priorita` int DEFAULT NULL,
  `stato_manutenzione` varchar(255) DEFAULT NULL,
  `luogo_id` bigint NOT NULL,
  `manutentore_email` varchar(255) DEFAULT NULL,
  `utente_richiedente_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsbxjghvxelgif0o9tk2hyl0d1` (`luogo_id`),
  KEY `FKp3h6g54wln9nmki3ykt6ligvv` (`manutentore_email`),
  KEY `FK70h1w4ptel03wvnj8le3chdmr` (`utente_richiedente_email`),
  CONSTRAINT `FK70h1w4ptel03wvnj8le3chdmr` FOREIGN KEY (`utente_richiedente_email`) REFERENCES `utente` (`email`),
  CONSTRAINT `FKp3h6g54wln9nmki3ykt6ligvv` FOREIGN KEY (`manutentore_email`) REFERENCES `utente` (`email`),
  CONSTRAINT `FKsbxjghvxelgif0o9tk2hyl0d1` FOREIGN KEY (`luogo_id`) REFERENCES `luogo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manutenzione`
--

LOCK TABLES `manutenzione` WRITE;
/*!40000 ALTER TABLE `manutenzione` DISABLE KEYS */;
/*!40000 ALTER TABLE `manutenzione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messaggio`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messaggio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_ora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_riferimento_messaggio` bigint NOT NULL,
  `primo_utente` bit(1) NOT NULL,
  `testo` longtext,
  `tipo_messaggio` enum('IMMAGINE','MANUTENZIONE','TESTO') DEFAULT NULL,
  `id_chat` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8hu8269b0bb5128colr670uns` (`id_chat`),
  CONSTRAINT `FK8hu8269b0bb5128colr670uns` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messaggio`
--

LOCK TABLES `messaggio` WRITE;
/*!40000 ALTER TABLE `messaggio` DISABLE KEYS */;
/*!40000 ALTER TABLE `messaggio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `residente_luogo`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `residente_luogo` (
  `residente_email` varchar(255) NOT NULL,
  `luogo_id` bigint NOT NULL,
  KEY `FKbjbuhqrgx42art1c6jwudbp6t` (`luogo_id`),
  KEY `FKp7yhn6dnkmxbol4tr2x558gr5` (`residente_email`),
  CONSTRAINT `FKbjbuhqrgx42art1c6jwudbp6t` FOREIGN KEY (`luogo_id`) REFERENCES `luogo` (`id`),
  CONSTRAINT `FKp7yhn6dnkmxbol4tr2x558gr5` FOREIGN KEY (`residente_email`) REFERENCES `utente` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `residente_luogo`
--

LOCK TABLES `residente_luogo` WRITE;
/*!40000 ALTER TABLE `residente_luogo` DISABLE KEYS */;
/*!40000 ALTER TABLE `residente_luogo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `ruolo` varchar(31) NOT NULL,
  `email` varchar(255) NOT NULL,
  `bloccato` bit(1) NOT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `data_di_nascita` date DEFAULT NULL,
  `email_bloccate` bit(1) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tipo_manutentore` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('Admin','admin@gmail.com',_binary '\0','Nardella','2003-05-17',_binary '\0','Dario','$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i',NULL);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-03  9:38:03
