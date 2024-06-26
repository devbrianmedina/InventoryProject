CREATE SCHEMA IF NOT EXISTS inventory_db12 DEFAULT CHARACTER SET utf8mb4;
USE inventory_db12;

CREATE TABLE IF NOT EXISTS productos (
    idProducto INT(6) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT(6) NOT NULL DEFAULT 0,
    estatus ENUM('Activo', 'Inactivo') NULL DEFAULT 'Activo',
    PRIMARY KEY (idProducto)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS roles (
    idRol INT(2) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (idRol)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS usuarios (
    idUsuario INT(6) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    idRol INT(2) NOT NULL,
    estatus INT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (idUsuario),
    UNIQUE INDEX correo (correo ASC),
    INDEX fk_usuarios_roles1_idx (idRol ASC),
    CONSTRAINT fk_usuarios_roles1
    FOREIGN KEY (idRol)
    REFERENCES roles (idRol)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS movimientos (
    idMovimiento INT(6) NOT NULL AUTO_INCREMENT,
    idProducto INT(6) NOT NULL,
    tipo ENUM('Entrada', 'Salida') NOT NULL,
    cantidad INT(6) NOT NULL,
    fechaHora DATETIME NULL DEFAULT CURRENT_TIMESTAMP(),
    idUsuario INT(6) NOT NULL,
    PRIMARY KEY (idMovimiento),
    INDEX idProducto (idProducto ASC),
    INDEX idUsuario (idUsuario ASC),
    CONSTRAINT movimientos_ibfk_1
    FOREIGN KEY (idProducto)
    REFERENCES productos (idProducto),
    CONSTRAINT movimientos_ibfk_2
    FOREIGN KEY (idUsuario)
    REFERENCES usuarios (idUsuario)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- INSERTS
INSERT INTO roles VALUES(1, 'Administrador');
INSERT INTO roles VALUES(2, 'Almacenista');
INSERT INTO usuarios VALUES(1, 'Brian', 'brian@codeteralab.com', '1234', 1, 1);
INSERT INTO usuarios VALUES(2, 'Francisco', 'francisco@codeteralab.com', '1234', 2, 1);

-- TRIGGER para registrar movimientos
DELIMITER //

CREATE TRIGGER after_product_update
    AFTER UPDATE ON productos
    FOR EACH ROW
BEGIN
    DECLARE current_user_id INT;

    SET current_user_id = @current_user_id;

    -- Insertar un registro de salida en la tabla de movimientos
    IF NEW.cantidad < OLD.cantidad THEN
    	INSERT INTO movimientos (idProducto, tipo, cantidad, idUsuario)
    	VALUES (OLD.idProducto, 'Salida', OLD.cantidad - NEW.cantidad, current_user_id);
    END IF;

    -- Insertar un registro de salida en la tabla de movimientos
    IF NEW.cantidad > OLD.cantidad THEN
        INSERT INTO movimientos (idProducto, tipo, cantidad, idUsuario)
        VALUES (NEW.idProducto, 'Entrada', NEW.cantidad - OLD.cantidad, current_user_id);
    END IF;
END;
//

DELIMITER ;