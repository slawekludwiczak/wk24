CREATE DATABASE budget CHARACTER SET utf8 COLLATE utf8_polish_ci;
CREATE TABLE budget.budget (
                               `id` INT PRIMARY KEY AUTO_INCREMENT,
                               `type` ENUM('IN', 'OUT') NOT NULL,
                               `description` VARCHAR(200),
                               `amount` DOUBLE NOT NULL,
                               `date` DATE NOT NULL
);