CREATE DATABASE IF NOT EXISTS `card_organizer`;
USE `card_organizer`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `box`;
CREATE TABLE `box`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `name`     varchar(45) NOT NULL,
    `location` varchar(45) DEFAULT NULL,
    `color`    varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS `deck_card`;
CREATE TABLE `deck_card`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `box_id`    int(11)     NOT NULL,
    `name`      varchar(45) NOT NULL,
    `deck_name` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_BOX_DECK_idx` (`box_id`),
    CONSTRAINT `FK_BOX_DECK` FOREIGN KEY (`box_id`)
        REFERENCES `box` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS `collection_card`;
CREATE TABLE `collection_card`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `box_id`   int(11)     NOT NULL,
    `name`     varchar(45) NOT NULL,
    `set_name` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_BOX_COLLECTION_idx` (`box_id`),
    CONSTRAINT `FK_BOX_COLLECTION` FOREIGN KEY (`box_id`)
        REFERENCES `box` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS `loan_card`;
CREATE TABLE `loan_card`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT,
    `box_id`          int(11)     NOT NULL,
    `name`            varchar(45) NOT NULL,
    `owner_firstname` varchar(45) DEFAULT NULL,
    `owner_lastname`  varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_BOX_LOAN_idx` (`box_id`),
    CONSTRAINT `FK_BOX_LOAN` FOREIGN KEY (`box_id`)
        REFERENCES `box` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

