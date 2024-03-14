
-- -----------------------------------------------------
-- Table `stageavenirapi`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`role` (
    `idRole` INT NOT NULL AUTO_INCREMENT,
    `nom` ENUM('etudiant', 'coordonnateur', 'employeur','admin') NOT NULL,
    PRIMARY KEY (`idRole`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`categorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`categorie` (
    `idcategorie` INT NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idcategorie`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`utilisateur` (
    `idutilisateur` VARCHAR(255) NOT NULL ,
    `nom` VARCHAR(45) NOT NULL,
    `prenom` VARCHAR(45) NOT NULL,
    `courriel` VARCHAR(45) NOT NULL,
    `telephone` VARCHAR(45) NULL,
    `categorie_idcategorie` INT NOT NULL,
    `role_idRole` INT NOT NULL,
    PRIMARY KEY (`idutilisateur`),
    INDEX `fk_utilisateur_role1_idx` (`role_idRole` ASC) VISIBLE,
    INDEX `fk_utilisateur_categorie1_idx` (`categorie_idcategorie` ASC) VISIBLE,
    CONSTRAINT `fk_utilisateur_role1`
    FOREIGN KEY (`role_idRole`)
    REFERENCES `stageavenirapi`.`role` (`idRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_utilisateur_categorie1`
    FOREIGN KEY (`categorie_idcategorie`)
    REFERENCES `stageavenirapi`.`categorie` (`idcategorie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`demandeStage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`demandeStage` (
    `iddemandeStage` INT NOT NULL AUTO_INCREMENT,
    `titre` VARCHAR(45) NOT NULL,
    `description` TINYINT NOT NULL,
    `remunere` TINYINT NOT NULL DEFAULT 0,
    `poste` VARCHAR(45) NOT NULL,
    `visible` TINYINT NOT NULL DEFAULT 1,
    `categorie_idcategorie` INT NOT NULL,
    `utilisateur_idutilisateur` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`iddemandeStage`),
    INDEX `fk_demandeStage_utilisateur1_idx` (`utilisateur_idutilisateur` ASC) VISIBLE,
    INDEX `fk_demandeStage_categorie1_idx` (`categorie_idcategorie` ASC) VISIBLE,
    CONSTRAINT `fk_demandeStage_utilisateur1`
    FOREIGN KEY (`utilisateur_idutilisateur`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_demandeStage_categorie1`
    FOREIGN KEY (`categorie_idcategorie`)
    REFERENCES `stageavenirapi`.`categorie` (`idcategorie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`entreprise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`entreprise` (
    `identreprise` INT NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(45) NOT NULL,
    `adresse` VARCHAR(45) NOT NULL,
    `description` VARCHAR(45) NOT NULL,
    `secteur` VARCHAR(45) NOT NULL,
    `utilisateur_idutilisateur` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`identreprise`),
    INDEX `fk_entreprise_utilisateur1_idx` (`utilisateur_idutilisateur` ASC) VISIBLE,
    CONSTRAINT `fk_entreprise_utilisateur1`
    FOREIGN KEY (`utilisateur_idutilisateur`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`offreStage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`offreStage` (
    `idoffreStage` INT NOT NULL AUTO_INCREMENT,
    `titre` VARCHAR(45) NOT NULL,
    `description` VARCHAR(45) NOT NULL,
    `poste_offert` VARCHAR(45) NOT NULL,
    `remunere` TINYINT NOT NULL,
    `date` DATE NOT NULL,
    `visible` TINYINT NOT NULL DEFAULT 1,
    `etat` ENUM('EN_COURS', 'ACCEPTEE', 'REFUSEE', 'ANNULEE') NOT NULL DEFAULT 'EN_COURS',
    `categorie_idcategorie` INT NOT NULL,
    `entreprise_identreprise` INT NOT NULL,
    PRIMARY KEY (`idoffreStage`),
    INDEX `fk_offreStage_categorie1_idx` (`categorie_idcategorie` ASC) VISIBLE,
    INDEX `fk_offreStage_entreprise1_idx` (`entreprise_identreprise` ASC) VISIBLE,
    CONSTRAINT `fk_offreStage_categorie1`
    FOREIGN KEY (`categorie_idcategorie`)
    REFERENCES `stageavenirapi`.`categorie` (`idcategorie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_offreStage_entreprise1`
    FOREIGN KEY (`entreprise_identreprise`)
    REFERENCES `stageavenirapi`.`entreprise` (`identreprise`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`candidature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`candidature` (
    `idcandidature` INT NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(45) NOT NULL,
    `etat` ENUM('EN_COURS', 'ACCEPTEE', 'REFUSEE', 'ANNULEE') NOT NULL DEFAULT 'EN_COURS',
    `utilisateur_idutilisateur` VARCHAR(255) NOT NULL,
    `offreStage_idoffreStage` INT NOT NULL,
    PRIMARY KEY (`idcandidature`),
    INDEX `fk_candidature_offreStage1_idx` (`offreStage_idoffreStage` ASC) VISIBLE,
    INDEX `fk_candidature_utilisateur1_idx` (`utilisateur_idutilisateur` ASC) VISIBLE,
    CONSTRAINT `fk_candidature_offreStage1`
    FOREIGN KEY (`offreStage_idoffreStage`)
    REFERENCES `stageavenirapi`.`offreStage` (`idoffreStage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_candidature_utilisateur1`
    FOREIGN KEY (`utilisateur_idutilisateur`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`document` (
    `iddocument` INT NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(255) NOT NULL,
    `type` ENUM('CV', 'SUPPLEMENT') NOT NULL,
    `contenu` BLOB NOT NULL,
    `utilisateur_idutilisateur` VARCHAR(255) NULL,
    `demandeStage_iddemandeStage` INT NULL,
    `candidature_idcandidature` INT NULL,
    PRIMARY KEY (`iddocument`),
    INDEX `fk_document_utilisateur_idx` (`utilisateur_idutilisateur` ASC) VISIBLE,
    INDEX `fk_document_demandeStage1_idx` (`demandeStage_iddemandeStage` ASC) VISIBLE,
    INDEX `fk_document_candidature1_idx` (`candidature_idcandidature` ASC) VISIBLE,
    CONSTRAINT `fk_document_utilisateur`
    FOREIGN KEY (`utilisateur_idutilisateur`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_demandeStage1`
    FOREIGN KEY (`demandeStage_iddemandeStage`)
    REFERENCES `stageavenirapi`.`demandeStage` (`iddemandeStage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_candidature1`
    FOREIGN KEY (`candidature_idcandidature`)
    REFERENCES `stageavenirapi`.`candidature` (`idcandidature`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`competence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`competence` (
    `idcompetence` INT NOT NULL AUTO_INCREMENT,
    `demandeStage_iddemandeStage` INT NOT NULL,
    `nom` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idcompetence`),
    INDEX `fk_competence_demandeStage1_idx` (`demandeStage_iddemandeStage` ASC) VISIBLE,
    CONSTRAINT `fk_competence_demandeStage1`
    FOREIGN KEY (`demandeStage_iddemandeStage`)
    REFERENCES `stageavenirapi`.`demandeStage` (`iddemandeStage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`accordStage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`accordStage` (
     `idaccordStage` INT NOT NULL AUTO_INCREMENT,
    `commentaire` VARCHAR(45) NULL,
    `etat` ENUM('EN_COURS', 'ACCEPTEE', 'REFUSEE', 'ANNULEE') NOT NULL DEFAULT 'EN_COURS',
    `utilisateur_idutilisateur` VARCHAR(255) NOT NULL,
    `offreStage_idoffreStage` INT NOT NULL,
    PRIMARY KEY (`idaccordStage`),
    INDEX `fk_accordStage_utilisateur1_idx` (`utilisateur_idutilisateur` ASC) VISIBLE,
    INDEX `fk_accordStage_offreStage1_idx` (`offreStage_idoffreStage` ASC) VISIBLE,
    CONSTRAINT `fk_accordStage_utilisateur1`
    FOREIGN KEY (`utilisateur_idutilisateur`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_accordStage_offreStage1`
    FOREIGN KEY (`offreStage_idoffreStage`)
    REFERENCES `stageavenirapi`.`offreStage` (`idoffreStage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`proposition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`proposition` (
    `idproposition` INT NOT NULL AUTO_INCREMENT,
    `message` VARCHAR(45) NOT NULL,
    `etat` ENUM('EN_COURS', 'ACCEPTEE', 'REFUSEE', 'ANNULEE') NOT NULL DEFAULT 'EN_COURS',
    `demandeStage_iddemandeStage` INT NOT NULL,
    PRIMARY KEY (`idproposition`),
    INDEX `fk_proposition_demandeStage1_idx` (`demandeStage_iddemandeStage` ASC) VISIBLE,
    CONSTRAINT `fk_proposition_demandeStage1`
    FOREIGN KEY (`demandeStage_iddemandeStage`)
    REFERENCES `stageavenirapi`.`demandeStage` (`iddemandeStage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stageavenirapi`.`utilisateur_has_utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stageavenirapi`.`utilisateur_has_utilisateur` (
   `utilisateur_idutilisateur` VARCHAR(255) NOT NULL ,
    `utilisateur_idutilisateur1` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`utilisateur_idutilisateur`, `utilisateur_idutilisateur1`),
    INDEX `fk_utilisateur_has_utilisateur_utilisateur2_idx` (`utilisateur_idutilisateur1` ASC) VISIBLE,
    INDEX `fk_utilisateur_has_utilisateur_utilisateur1_idx` (`utilisateur_idutilisateur` ASC) VISIBLE,
    CONSTRAINT `fk_utilisateur_has_utilisateur_utilisateur1`
    FOREIGN KEY (`utilisateur_idutilisateur`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_utilisateur_has_utilisateur_utilisateur2`
    FOREIGN KEY (`utilisateur_idutilisateur1`)
    REFERENCES `stageavenirapi`.`utilisateur` (`idutilisateur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;






