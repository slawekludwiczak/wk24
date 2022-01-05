CREATE TABLE budget (
                               `id` INT PRIMARY KEY AUTO_INCREMENT,
                               `type` VARCHAR(10) NOT NULL,
                               `description` VARCHAR(200),
                               `amount` DOUBLE NOT NULL,
                               `date` DATE NOT NULL
);
