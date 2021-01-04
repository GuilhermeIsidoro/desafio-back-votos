SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema desafio_votos
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `desafio_votos` ;

-- -----------------------------------------------------
-- Schema desafio_votos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `desafio_votos` DEFAULT CHARACTER SET utf8 ;
USE `desafio_votos` ;

-- -----------------------------------------------------
-- Table `desafio_votos`.`agenda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desafio_votos`.`agenda` ;

CREATE TABLE IF NOT EXISTS `desafio_votos`.`agenda` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `agenda_name` VARCHAR(100) NOT NULL,
  `agenda_description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desafio_votos`.`voting_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desafio_votos`.`voting_session` ;

CREATE TABLE IF NOT EXISTS `desafio_votos`.`voting_session` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `agenda_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `agenda_id`),
  INDEX `fk_voting_session_agenda_idx` (`agenda_id` ASC),
  CONSTRAINT `fk_voting_session_agenda`
    FOREIGN KEY (`agenda_id`)
    REFERENCES `desafio_votos`.`agenda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desafio_votos`.`associate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desafio_votos`.`associate` ;

CREATE TABLE IF NOT EXISTS `desafio_votos`.`associate` (
  `id` VARCHAR(11) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desafio_votos`.`vote_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desafio_votos`.`vote_type` ;

CREATE TABLE IF NOT EXISTS `desafio_votos`.`vote_type` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `vote_description` VARCHAR(45) NOT NULL,
  `agenda_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `agenda_id`),
  INDEX `fk_vote_type_agenda1_idx` (`agenda_id` ASC),
  CONSTRAINT `fk_vote_type_agenda1`
    FOREIGN KEY (`agenda_id`)
    REFERENCES `desafio_votos`.`agenda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desafio_votos`.`vote`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desafio_votos`.`vote` ;

CREATE TABLE IF NOT EXISTS `desafio_votos`.`vote` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `agenda_id` BIGINT NOT NULL,
  `associate_id` VARCHAR(11) NOT NULL,
  `voting_date` DATETIME NOT NULL,
  `vote_type_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `agenda_id`, `associate_id`, `vote_type_id`),
  INDEX `fk_vote_agenda1_idx` (`agenda_id` ASC),
  INDEX `fk_vote_associate1_idx` (`associate_id` ASC),
  INDEX `fk_vote_vote_type1_idx` (`vote_type_id` ASC),
  CONSTRAINT `fk_vote_agenda1`
    FOREIGN KEY (`agenda_id`)
    REFERENCES `desafio_votos`.`agenda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vote_associate1`
    FOREIGN KEY (`associate_id`)
    REFERENCES `desafio_votos`.`associate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vote_vote_type1`
    FOREIGN KEY (`vote_type_id`)
    REFERENCES `desafio_votos`.`vote_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
