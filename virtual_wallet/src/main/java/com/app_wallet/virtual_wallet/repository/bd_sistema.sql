-- Crear la base de datos
CREATE DATABASE sistema;

-- Usar la base de datos creada
USE sistema;

-- Tabla para notificaciones
CREATE TABLE notification_entity (
    id BIGINT NOT NULL AUTO_INCREMENT,
    date DATETIME(6),
    message VARCHAR(255),
    `read` BIT NOT NULL,  -- `read` rodeado por backticks para evitar conflicto
    user_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Tabla para puntos del sistema
CREATE TABLE system_points_entity (
    id BIGINT NOT NULL AUTO_INCREMENT,
    accumulated_points INT NOT NULL,
    `range` ENUM('BRONZE', 'GOLD', 'PLATINUM', 'SILVER'),  -- `range` rodeado por backticks
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Tabla para usuarios (ajustada a tus necesidades)
CREATE TABLE user_entity (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    points_id BIGINT,  -- Relación con la tabla de puntos
    PRIMARY KEY (id),
    FOREIGN KEY (points_id) REFERENCES system_points_entity(id)  -- Relación con system_points_entity
) ENGINE=InnoDB;

USE sistema;
SELECT * FROM user_entity; 

