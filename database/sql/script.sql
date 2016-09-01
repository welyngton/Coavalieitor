-- MySQL Script generated by MySQL Workbench
-- 09/01/16 00:59:59
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema coavalieitor_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema coavalieitor_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `coavalieitor_db` DEFAULT CHARACTER SET utf8 ;
USE `coavalieitor_db` ;

-- -----------------------------------------------------
-- Table `coavalieitor_db`.`professor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coavalieitor_db`.`professor` ;

CREATE TABLE IF NOT EXISTS `coavalieitor_db`.`professor` (
  `id_prof` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_prof`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
