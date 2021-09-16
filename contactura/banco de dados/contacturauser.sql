CREATE TABLE `contactura`.`ContacturaUser` (

`id` INT NOT NULL,
`username` VARCHAR(255) NOT NULL,
`password` VARCHAR(255) NOT NULL,
`name` VARCHAR(255) NOT NULL,
`admin` TINYINT NOT NULL,
PRIMARY KEY(`id`));

ALTER TABLE `contactura`.`ContacturaUser`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT, 
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
