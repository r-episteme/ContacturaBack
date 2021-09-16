CREATE TABLE `contactura`.`contactura` (
`id` int not null,
`name` VARCHAR(255) NULL,
`email` VARCHAR(255) NULL, 
`phone` VARCHAR(45) NULL,
PRIMARY KEY(`id`));

ALTER TABLE `contactura`.`contactura`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT,
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);