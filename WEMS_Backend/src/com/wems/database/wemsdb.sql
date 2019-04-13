-- MySQL dump 10.13  Distrib 5.5.15, for Win32 (x86)
--
-- Host: localhost    Database: wemsdb
-- ------------------------------------------------------
-- Server version	5.5.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Current Database: `wemsdb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS */ `wemsdb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `wemsdb`;

--
-- Table structure for table `alert_log`
--

DROP TABLE IF EXISTS `alert_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert_log`
(
    `alertsId`       int(11)     NOT NULL AUTO_INCREMENT,
    `alertType`      varchar(45)          DEFAULT NULL,
    `timestamp`      datetime    NOT NULL,
    `alertPriority`  varchar(45) NOT NULL DEFAULT 'Normal',
    `sensorDatadump` blob,
    `alertSettingId` int(11)              DEFAULT NULL,
    PRIMARY KEY (`alertsId`),
    KEY `alertLog_alertSetting_fk` (`alertSettingId`),
    CONSTRAINT `alertLog_alertSetting_fk` FOREIGN KEY (`alertSettingId`) REFERENCES `alert_setting` (`alertId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = latin1 COMMENT ='Table for system settings';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_log`
--

LOCK TABLES `alert_log` WRITE;
/*!40000 ALTER TABLE `alert_log`
    DISABLE KEYS */;
INSERT INTO `alert_log`
VALUES (1, 'Warning', '2011-10-30 19:00:00', 'Low', NULL, 0),
       (2, 'Warning', '2011-10-30 19:00:30', 'Normal', NULL, 0),
       (3, 'Warning', '2011-10-30 19:01:00', 'Normal', NULL, 0),
       (4, 'Warning', '2011-10-30 19:01:30', 'High', NULL, 0),
       (5, 'Warning', '2011-10-30 19:02:00', 'Normal', NULL, 0),
       (6, 'Warning', '2011-10-30 21:02:30', 'Normal', NULL, 0),
       (7, 'Warning', '2011-10-30 21:03:00', 'Normal', NULL, 0),
       (8, 'Warning', '2011-10-30 21:03:30', 'Normal', NULL, 0),
       (9, 'Error', '2011-10-30 21:04:00', 'High', NULL, 0),
       (10, 'Fault', '2011-10-30 21:04:30', 'Normal', NULL, 0),
       (11, 'Error', '2011-10-30 21:06:22', 'High', NULL, 0),
       (12, 'Warning', '2011-10-30 21:09:22', 'Normal', NULL, 0),
       (13, 'Warning', '2011-10-30 21:16:22', 'Normal', NULL, 0),
       (14, 'Warning', '2011-10-30 21:24:22', 'Normal', NULL, 0),
       (15, 'Error', '2011-10-30 21:30:22', 'High', NULL, 0),
       (16, 'Fault', '2011-10-30 21:31:22', 'High', NULL, 0),
       (17, 'Warning', '2011-10-30 21:38:22', 'High', NULL, 0),
       (18, 'Warning', '2011-10-30 21:39:22', 'Low', NULL, 0);
/*!40000 ALTER TABLE `alert_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert_setting`
--

DROP TABLE IF EXISTS `alert_setting`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert_setting`
(
    `alertId`               int(11)     NOT NULL AUTO_INCREMENT,
    `alertType`             varchar(45)          DEFAULT NULL,
    `timestamp`             datetime    NOT NULL,
    `alertPriority`         varchar(45) NOT NULL DEFAULT 'Normal',
    `minThresholdValue`     double               DEFAULT NULL,
    `minThresholdEnabled`   tinyint(1)  NOT NULL DEFAULT '0',
    `maxThresholdValue`     double               DEFAULT NULL,
    `maxThresholdEnabled`   tinyint(1)  NOT NULL DEFAULT '0',
    `exactThresholdValue`   double               DEFAULT NULL,
    `exactThresholdEnabled` tinyint(1)  NOT NULL DEFAULT '0',
    `alertUserGroup`        int(11)              DEFAULT NULL,
    `score`                 int(11)              DEFAULT NULL,
    `scoreEnabled`          tinyint(1)  NOT NULL DEFAULT '0',
    `dataParameterId`       int(11)     NOT NULL,
    PRIMARY KEY (`alertId`),
    KEY `alertSetting_userGroup_fk` (`alertUserGroup`),
    KEY `setting_parameter_fk` (`dataParameterId`),
    CONSTRAINT `alertSetting_userGroup_fk` FOREIGN KEY (`alertUserGroup`) REFERENCES `user_group` (`userGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `setting_parameter_fk` FOREIGN KEY (`dataParameterId`) REFERENCES `sensordata_parameter` (`parameteruid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = latin1 COMMENT ='Table for system settings';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_setting`
--

LOCK TABLES `alert_setting` WRITE;
/*!40000 ALTER TABLE `alert_setting`
    DISABLE KEYS */;
INSERT INTO `alert_setting`
VALUES (1, 'NORMAL', '2011-10-30 19:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 1, 1, 1, 0),
       (2, 'NORMAL', '2011-10-30 19:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 2, 1, 1, 0),
       (3, 'NORMAL', '2011-10-30 19:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 3, 1, 1, 0),
       (4, 'NORMAL', '2011-10-30 19:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 4, 1, 1, 0),
       (5, 'NORMAL', '2011-10-30 19:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 5, 1, 1, 0),
       (6, 'NORMAL', '2011-10-30 19:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 6, 1, 1, 0),
       (7, 'NORMAL', '2011-10-30 21:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 1, 1, 1, 0),
       (8, 'NORMAL', '2011-10-30 21:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 2, 1, 1, 0),
       (9, 'NORMAL', '2011-10-30 21:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 3, 1, 1, 0),
       (10, 'NORMAL', '2011-10-30 21:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 4, 1, 1, 0),
       (11, 'NORMAL', '2011-10-30 21:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 5, 1, 1, 0),
       (12, 'NORMAL', '2011-10-30 21:00:00', 'Normal', NULL, 0, NULL, 0, NULL, 0, 6, 1, 1, 0);
/*!40000 ALTER TABLE `alert_setting`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurations`
--

DROP TABLE IF EXISTS `configurations`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurations`
(
    `configurationsId` int(11)     NOT NULL AUTO_INCREMENT,
    `systemName`       varchar(200) DEFAULT NULL,
    `maxUsers`         int(11)      DEFAULT NULL,
    `currentUsers`     varchar(45) NOT NULL,
    PRIMARY KEY (`configurationsId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = latin1 COMMENT ='Table for system settings';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurations`
--

LOCK TABLES `configurations` WRITE;
/*!40000 ALTER TABLE `configurations`
    DISABLE KEYS */;
INSERT INTO `configurations`
VALUES (1, 'System1', 10, '9'),
       (2, 'System2', 10, '8'),
       (3, 'System3', 10, '7'),
       (4, 'System4', 10, '6'),
       (5, 'System5', 10, '5'),
       (6, 'System6', 10, '6'),
       (7, 'System7', 10, '7'),
       (8, 'System8', 10, '10'),
       (9, 'System9', 10, '2'),
       (10, 'System10', 10, '7');
/*!40000 ALTER TABLE `configurations`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_log`
--

DROP TABLE IF EXISTS `events_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_log`
(
    `eventsId`  int(11)  NOT NULL AUTO_INCREMENT,
    `deviceId`  int(11) DEFAULT NULL,
    `timestamp` datetime NOT NULL,
    `eventType` int(11) DEFAULT NULL,
    `alertId`   int(11) DEFAULT NULL,
    `roomId`    int(11) DEFAULT NULL,
    PRIMARY KEY (`eventsId`),
    KEY `events_device_fk` (`deviceId`),
    KEY `events_alertLog_fk` (`alertId`),
    KEY `events_room_fk` (`roomId`),
    CONSTRAINT `events_room_fk` FOREIGN KEY (`roomId`) REFERENCES `sensordata_room` (`roomuid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `events_alertLog_fk` FOREIGN KEY (`alertId`) REFERENCES `alert_log` (`alertsId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `events_device_fk` FOREIGN KEY (`deviceId`) REFERENCES `sensordata_device` (`deviceuid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = latin1 COMMENT ='Table for system settings';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_log`
--

LOCK TABLES `events_log` WRITE;
/*!40000 ALTER TABLE `events_log`
    DISABLE KEYS */;
INSERT INTO `events_log`
VALUES (1, 1, '2011-10-30 19:00:00', 1, 1, 4),
       (2, 2, '2011-10-30 19:00:30', 2, 2, NULL),
       (3, 3, '2011-10-30 19:01:00', 1, 3, NULL),
       (4, 4, '2011-10-30 19:01:30', 1, 4, NULL),
       (5, 5, '2011-10-30 19:02:00', 1, 5, NULL),
       (6, 1, '2011-10-30 19:02:30', 1, 6, 4),
       (7, 2, '2011-10-30 19:03:00', 1, 7, NULL),
       (8, 3, '2011-10-30 19:03:30', 1, NULL, NULL),
       (9, 4, '2011-10-30 19:04:00', 2, 8, 2),
       (10, 5, '2011-10-30 19:04:30', 2, NULL, NULL),
       (11, 1, '2011-10-30 19:04:30', 2, 9, 1),
       (12, 2, '2011-10-30 19:04:30', 1, 10, NULL),
       (13, 3, '2011-10-30 19:04:30', 1, 11, 6),
       (14, 4, '2011-10-30 19:04:30', 2, NULL, NULL),
       (15, 5, '2011-10-30 19:04:30', 1, NULL, 3),
       (16, 1, '2011-10-30 19:04:30', 1, NULL, NULL),
       (17, 2, '2011-10-30 19:04:30', 2, NULL, 4),
       (18, 3, '2011-10-30 19:04:30', 1, NULL, NULL),
       (19, 4, '2011-10-30 19:04:30', 2, NULL, 4),
       (20, 5, '2011-10-30 19:04:30', 2, 12, NULL),
       (21, 1, '2011-10-30 19:04:30', 2, 13, NULL),
       (22, 2, '2011-10-30 19:04:30', 1, 14, 4),
       (23, 3, '2011-10-30 19:04:30', 2, 15, NULL),
       (24, 4, '2011-10-30 19:04:30', 2, NULL, 4),
       (25, 5, '2011-10-30 19:04:30', 2, NULL, 4);
/*!40000 ALTER TABLE `events_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensordata_address`
--

DROP TABLE IF EXISTS `sensordata_address`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensordata_address`
(
    `addressuid`   int(11)      NOT NULL AUTO_INCREMENT,
    `streetnumber` varchar(10)  NOT NULL,
    `streetname`   varchar(200) NOT NULL,
    `suburb`       varchar(200) NOT NULL,
    `state`        varchar(100) NOT NULL,
    `building`     varchar(50) DEFAULT NULL,
    `level`        varchar(50) DEFAULT NULL,
    `userGroup`    int(11)     DEFAULT NULL,
    PRIMARY KEY (`addressuid`),
    KEY `address_usergroup_fk` (`userGroup`),
    CONSTRAINT `address_usergroup_fk` FOREIGN KEY (`userGroup`) REFERENCES `user_group` (`userGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensordata_address`
--

LOCK TABLES `sensordata_address` WRITE;
/*!40000 ALTER TABLE `sensordata_address`
    DISABLE KEYS */;
INSERT INTO `sensordata_address`
VALUES (1, '15', 'Broadway', 'Ultimo', 'NSW', '1', '1', 1),
       (2, '15', 'Broadway', 'Ultimo', 'NSW', '1', '1', 1),
       (3, '15', 'Broadway', 'Ultimo', 'NSW', '1', '1', 1),
       (4, '15', 'Broadway', 'Ultimo', 'NSW', '1', '1', 1),
       (5, '15', 'Broadway', 'Ultimo', 'NSW', '1', '4', 1),
       (6, '15', 'Broadway', 'Ultimo', 'NSW', '1', '4', 1),
       (7, '15', 'Broadway', 'Ultimo', 'NSW', '1', '4', 1),
       (8, '15', 'Broadway', 'Ultimo', 'NSW', '1', '4', 1),
       (9, '15', 'Broadway', 'Ultimo', 'NSW', '2', '4', 1),
       (10, '15', 'Broadway', 'Ultimo', 'NSW', '2', '4', 1);
/*!40000 ALTER TABLE `sensordata_address`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensordata_device`
--

DROP TABLE IF EXISTS `sensordata_device`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensordata_device`
(
    `deviceuid`  int(11)    NOT NULL AUTO_INCREMENT,
    `id`         int(11)    NOT NULL,
    `type`       varchar(250) DEFAULT NULL,
    `brand`      varchar(250) DEFAULT NULL,
    `model`      varchar(250) DEFAULT NULL,
    `serial`     varchar(250) DEFAULT NULL,
    `macaddress` varchar(20)  DEFAULT NULL,
    `connected`  tinyint(1) NOT NULL,
    `roomuid`    int(11)    NOT NULL,
    `userGroup`  int(11)      DEFAULT NULL,
    PRIMARY KEY (`deviceuid`),
    KEY `device_roomuid_fk` (`roomuid`),
    KEY `device_userGroup_fk` (`userGroup`),
    CONSTRAINT `device_roomuid_fk` FOREIGN KEY (`roomuid`) REFERENCES `sensordata_room` (`roomuid`) ON UPDATE CASCADE,
    CONSTRAINT `device_userGroup_fk` FOREIGN KEY (`userGroup`) REFERENCES `user_group` (`userGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensordata_device`
--

LOCK TABLES `sensordata_device` WRITE;
/*!40000 ALTER TABLE `sensordata_device`
    DISABLE KEYS */;
INSERT INTO `sensordata_device`
VALUES (1, 1, 'type1', 'AMX', 'M001', '11112222', '01-01-01-01-01-01', 1, 1, 1),
       (2, 1, 'type1', 'AMX', 'M001', '11113333', '01-02-01-01-01-01', 1, 1, 2),
       (3, 1, 'type2', 'AMX', 'M001', '11112222', '01-03-01-01-01-01', 1, 2, 3),
       (4, 1, 'type2', 'AMX', 'M002', '11114444', '01-04-01-01-01-01', 1, 2, 4),
       (5, 1, 'type3', 'AMX', 'M001', '11115555', '01-05-01-01-01-01', 1, 3, 5),
       (6, 1, 'type3', 'AMX', 'M002', '11116666', '01-06-01-01-01-01', 1, 4, 6),
       (7, 1, 'type4', 'AMX', 'M001', '11115555', '01-07-01-01-01-01', 1, 3, 7),
       (8, 1, 'type4', 'AMX', 'M002', '11116666', '01-08-01-01-01-01', 1, 4, 8),
       (9, 1, 'type4', 'AMX', 'M003', '11115555', '01-07-01-01-01-01', 1, 3, 9),
       (10, 1, 'type4', 'AMX', 'M004', '11116666', '01-08-01-01-01-01', 1, 4, 10);
/*!40000 ALTER TABLE `sensordata_device`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensordata_deviceusage`
--

DROP TABLE IF EXISTS `sensordata_deviceusage`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensordata_deviceusage`
(
    `deviceusageuid` int(11)    NOT NULL AUTO_INCREMENT,
    `powerstatus`    tinyint(1) NOT NULL,
    `time`           datetime   NOT NULL,
    `deviceuid`      int(11)    NOT NULL,
    PRIMARY KEY (`deviceusageuid`),
    KEY `deviceusage_device_fk` (`deviceuid`),
    CONSTRAINT `deviceusage_device_fk` FOREIGN KEY (`deviceuid`) REFERENCES `sensordata_device` (`deviceuid`) ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensordata_deviceusage`
--

LOCK TABLES `sensordata_deviceusage` WRITE;
/*!40000 ALTER TABLE `sensordata_deviceusage`
    DISABLE KEYS */;
INSERT INTO `sensordata_deviceusage`
VALUES (1, 1, '2011-10-30 19:00:00', 1),
       (2, 1, '2011-10-30 19:00:00', 2),
       (3, 1, '2011-10-30 19:00:00', 3),
       (4, 1, '2011-10-30 19:00:00', 4),
       (5, 1, '2011-10-30 19:00:00', 5),
       (6, 1, '2011-10-30 19:00:00', 6),
       (7, 1, '2011-10-30 21:00:00', 1),
       (8, 1, '2011-10-30 21:00:00', 2),
       (9, 1, '2011-10-30 21:00:00', 3),
       (10, 1, '2011-10-30 21:00:00', 4),
       (11, 1, '2011-10-30 21:00:00', 5),
       (12, 1, '2011-10-30 21:00:00', 6);
/*!40000 ALTER TABLE `sensordata_deviceusage`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensordata_parameter`
--

DROP TABLE IF EXISTS `sensordata_parameter`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensordata_parameter`
(
    `parameteruid` int(11)      NOT NULL AUTO_INCREMENT,
    `name`         varchar(150) NOT NULL,
    `value`        varchar(50)  NOT NULL,
    `deviceuid`    int(11)      NOT NULL,
    PRIMARY KEY (`parameteruid`),
    KEY `parameter_deviceuid_fk` (`deviceuid`),
    CONSTRAINT `parameter_deviceuid_fk` FOREIGN KEY (`deviceuid`) REFERENCES `sensordata_device` (`deviceuid`) ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensordata_parameter`
--

LOCK TABLES `sensordata_parameter` WRITE;
/*!40000 ALTER TABLE `sensordata_parameter`
    DISABLE KEYS */;
INSERT INTO `sensordata_parameter`
VALUES (1, 'parameter1', 'value1', 1),
       (2, 'parameter2', 'value1', 2),
       (3, 'parameter3', 'value1', 3),
       (4, 'parameter4', 'value1', 4),
       (5, 'parameter4', 'value2', 5),
       (6, 'parameter5', 'value1', 6),
       (7, 'parameter6', 'value1', 7),
       (8, 'parameter7', 'value1', 8),
       (9, 'parameter8', 'value1', 9),
       (10, 'parameter9', 'value1', 10);
/*!40000 ALTER TABLE `sensordata_parameter`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensordata_powerusage`
--

DROP TABLE IF EXISTS `sensordata_powerusage`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensordata_powerusage`
(
    `powerusageuid` int(11)  NOT NULL AUTO_INCREMENT,
    `time`          datetime NOT NULL,
    `watt`          double   NOT NULL,
    `roomuid`       int(11)  NOT NULL,
    PRIMARY KEY (`powerusageuid`),
    KEY `powerusage_room_fk` (`roomuid`),
    CONSTRAINT `powerusage_room_fk` FOREIGN KEY (`roomuid`) REFERENCES `sensordata_room` (`roomuid`) ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensordata_powerusage`
--

LOCK TABLES `sensordata_powerusage` WRITE;
/*!40000 ALTER TABLE `sensordata_powerusage`
    DISABLE KEYS */;
INSERT INTO `sensordata_powerusage`
VALUES (1, '2011-10-30 19:00:00', 20, 1),
       (2, '2011-10-30 19:00:00', 20, 2),
       (3, '2011-10-30 19:00:00', 20, 3),
       (4, '2011-10-30 19:00:00', 22, 4),
       (5, '2011-10-30 19:00:00', 20, 5),
       (6, '2011-10-30 19:00:00', 21, 6),
       (7, '2011-10-30 19:00:00', 20, 7),
       (8, '2011-10-30 19:00:00', 15, 8),
       (9, '2011-10-30 19:00:00', 17, 9),
       (10, '2011-10-30 19:00:00', 20, 10),
       (11, '2011-10-30 19:00:02', 23, 1),
       (12, '2011-10-30 19:00:02', 32, 2),
       (13, '2011-10-30 19:00:02', 12, 3),
       (14, '2011-10-30 19:00:02', 34, 4),
       (15, '2011-10-30 19:00:02', 3, 5),
       (16, '2011-10-30 19:00:02', 34, 6),
       (17, '2011-10-30 19:00:02', 54, 7),
       (18, '2011-10-30 19:00:02', 12, 8),
       (19, '2011-10-30 19:00:02', 65, 9),
       (20, '2011-10-30 19:00:02', 23, 10),
       (21, '2011-10-30 19:00:04', 2, 1),
       (22, '2011-10-30 19:00:04', 5, 2),
       (23, '2011-10-30 19:00:04', 34, 3),
       (24, '2011-10-30 19:00:04', 12, 4),
       (25, '2011-10-30 19:00:04', 65, 5),
       (26, '2011-10-30 19:00:04', 34, 6),
       (27, '2011-10-30 19:00:04', 65, 7),
       (28, '2011-10-30 19:00:04', 123, 8),
       (29, '2011-10-30 19:00:04', 54, 9),
       (30, '2011-10-30 19:00:04', 23, 10);
/*!40000 ALTER TABLE `sensordata_powerusage`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensordata_room`
--

DROP TABLE IF EXISTS `sensordata_room`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensordata_room`
(
    `roomuid`     int(11)     NOT NULL AUTO_INCREMENT,
    `id`          varchar(30) NOT NULL,
    `name`        varchar(100) DEFAULT NULL,
    `connected`   tinyint(1)   DEFAULT NULL,
    `powerstatus` tinyint(1)   DEFAULT NULL,
    `addressuid`  int(11)      DEFAULT NULL,
    `userGroup`   int(11)      DEFAULT NULL,
    PRIMARY KEY (`roomuid`),
    KEY `room_address_fk` (`addressuid`),
    KEY `room_usergroup_fk` (`userGroup`),
    CONSTRAINT `room_address_fk` FOREIGN KEY (`addressuid`) REFERENCES `sensordata_address` (`addressuid`) ON UPDATE CASCADE,
    CONSTRAINT `room_usergroup_fk` FOREIGN KEY (`userGroup`) REFERENCES `user_group` (`userGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensordata_room`
--

LOCK TABLES `sensordata_room` WRITE;
/*!40000 ALTER TABLE `sensordata_room`
    DISABLE KEYS */;
INSERT INTO `sensordata_room`
VALUES (1, 'type1_no1', 'sensortype1', 1, 1, 1, 1),
       (2, 'type1_no2', 'sensortype1', 1, 1, 1, 1),
       (3, 'type2_no1', 'sensortype2', 1, 1, 1, 2),
       (4, 'type2_no2', 'sensortype2', 1, 1, 1, 2),
       (5, 'type3_no1', 'sensortype3', 1, 1, 1, 3),
       (6, 'type3_no2', 'sensortype4', 1, 1, 1, 3),
       (7, 'type4_no1', 'sensortype4', 1, 1, 1, 4),
       (8, 'type4_no2', 'sensortype4', 1, 1, 1, 4),
       (9, 'type4_no3', 'sensortype4', 1, 1, 1, 5),
       (10, 'type4_no4', 'sensortype4', 1, 1, 1, 5);
/*!40000 ALTER TABLE `sensordata_room`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user`
(
    `userId`        int(11)      NOT NULL AUTO_INCREMENT,
    `userTypeId`    int(11)      NOT NULL,
    `firstName`     varchar(50)  NOT NULL,
    `lastName`      varchar(50)  NOT NULL,
    `username`      varchar(10)  NOT NULL,
    `email`         varchar(250) NOT NULL,
    `passHash`      varchar(45)  NOT NULL,
    `contactNumber` varchar(45)           DEFAULT NULL,
    `sessionId`     int(11)      NOT NULL,
    `loginAttempts` int(11)      NOT NULL,
    `lockedOut`     int(11)      NOT NULL,
    `userGroup`     int(11)               DEFAULT NULL,
    `isTempPass`    tinyint(1)   NOT NULL DEFAULT '0',
    PRIMARY KEY (`userId`),
    KEY `user_userType_fk` (`userTypeId`),
    KEY `user_userSession_fk` (`sessionId`),
    KEY `user_userGroup_fk` (`userGroup`),
    CONSTRAINT `user_userGroup_fk` FOREIGN KEY (`userGroup`) REFERENCES `user_group` (`userGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `user_userSession_fk` FOREIGN KEY (`sessionId`) REFERENCES `user_session` (`sessionId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `user_userType_fk` FOREIGN KEY (`userTypeId`) REFERENCES `user_type` (`userTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (13, 1, 'John', 'McCool', 'admin', 'admin@student.uts.edu.au', '21232f297a57a5a743894a0e4a801fc3', '1313131313', 13, 0, 0, 1, 0),
       (15, 1, 'ann', 'ann', 'ann2', 'dssdf', 'ann', '234', 1, 0, 0, 1, 0),
       (16, 1, 'ann', 'ann', 'ann3', 'oashdoias', '7e0d7f8a5d96c24ffcc840f31bce72b2', 'sajdas', 1, 0, 0, 1, 0);
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group`
(
    `userGroupId`          int(11)     NOT NULL AUTO_INCREMENT,
    `userGroupName`        varchar(45) NOT NULL,
    `userGroupDescription` varchar(250) DEFAULT NULL,
    `userGroupEmail`       varchar(45)  DEFAULT NULL,
    PRIMARY KEY (`userGroupId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group`
    DISABLE KEYS */;
INSERT INTO `user_group`
VALUES (1, 'b1l1r1g1', 'building 1 level 1 room 1 group 1', 'b1l1r1g1@uts.edu.au'),
       (2, 'b1l1r1g2', 'building 1 level 1 room 1 group 2', 'b1l1r1g2@uts.edu.au'),
       (3, 'b1l1r2g1', 'building 1 level 1 room 2 group 1', 'b1l1r2g1@uts.edu.au'),
       (4, 'b1l1r2g2', 'building 1 level 1 room 2 group 2', 'b1l1r2g2@uts.edu.au'),
       (5, 'b1l4r1g1', 'building 1 level 4 room 1 group 1', 'b1l4r1g1@uts.edu.au'),
       (6, 'b1l4r2g1', 'building 1 level 4 room 2 group 1', 'b1l4r2g1@uts.edu.au'),
       (7, 'b1l4r1g2', 'building 1 level 4 room 1 group 2', 'b1l4r1g2@uts.edu.au'),
       (8, 'b1l4r2g2', 'building 1 level 4 room 2 group 2', 'b1l4r2g2@uts.edu.au'),
       (9, 'b2l4r2g1', 'building 2 level 4 room 2 group 1', 'b2l4r2g1@uts.edu.au'),
       (10, 'b2l4r10g1', 'building 2 level 4 room 10 group 1', 'b2l4r10g1@uts.edu.au'),
       (11, 'Test', 'Testing', 'Duuu@dad.m');
/*!40000 ALTER TABLE `user_group`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_session`
(
    `sessionId`     int(11)     NOT NULL AUTO_INCREMENT,
    `sessionStatus` int(11)     NOT NULL,
    `loggedInTime`  datetime    NOT NULL,
    `ipAddress`     varchar(45) NOT NULL,
    PRIMARY KEY (`sessionId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session`
    DISABLE KEYS */;
INSERT INTO `user_session`
VALUES (1, 1, '2011-10-30 18:00:00', '138.25.174.179'),
       (2, 1, '2011-10-30 18:00:05', '138.25.174.180'),
       (3, 0, '2011-10-30 18:00:10', '138.25.174.181'),
       (4, 1, '2011-10-30 18:00:15', '138.25.174.182'),
       (5, 1, '2011-10-30 18:00:20', '138.25.174.183'),
       (6, 0, '2011-10-30 18:00:25', '138.25.174.184'),
       (7, 1, '2011-10-30 18:00:30', '138.25.174.185'),
       (8, 1, '2011-10-30 18:00:35', '138.25.174.186'),
       (9, 1, '2011-10-30 18:00:40', '138.25.174.187'),
       (10, 1, '2011-10-30 18:00:45', '138.25.174.188'),
       (11, 1, '2011-10-30 18:00:50', '138.25.174.189'),
       (12, 1, '2011-10-30 18:00:55', '138.25.174.190'),
       (13, 1, '2011-10-30 18:01:00', '138.25.174.191');
/*!40000 ALTER TABLE `user_session`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type`
(
    `userTypeId`          int(11)     NOT NULL AUTO_INCREMENT,
    `userTypeName`        varchar(45) NOT NULL DEFAULT 'Operator',
    `userTypeDescription` varchar(250)         DEFAULT NULL,
    PRIMARY KEY (`userTypeId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type`
    DISABLE KEYS */;
INSERT INTO `user_type`
VALUES (1, 'Admin', 'Administrator'),
       (2, 'Operator', 'Building 1 Operator'),
       (3, 'Operator', 'Building 1 Operator'),
       (4, 'Operator', 'Building 1 Operator'),
       (5, 'Operator', 'Building 2 Operator'),
       (6, 'Operator', 'Building 2 Operator'),
       (7, 'Operator', 'Building 2 Operator'),
       (8, 'Operator', 'Building 4 Operator'),
       (9, 'Operator', 'Building 4 Operator'),
       (10, 'Operator', 'Building 4 Operator'),
       (11, 'Technician', 'Building 1 Technician'),
       (12, 'Technician', 'Building 2 Technician'),
       (13, 'Technician', 'Building 4 Technician');
/*!40000 ALTER TABLE `user_type`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2011-11-11 18:36:40
