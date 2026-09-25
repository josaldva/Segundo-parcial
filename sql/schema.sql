-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS empresa_db;

-- 2. Seleccionar la base de datos
USE empresa_db;

-- 3. Crear la tabla 'empleados'
CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fecha_contratacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    tipo_contrato VARCHAR(20) NOT NULL DEFAULT 'Permanente', -- <--- Integrado (Mejora #4)
    
    CONSTRAINT chk_salario_positivo 
        CHECK (salario > 0)
);

-- 4. Trigger para evitar fechas futuras al INSERTAR
DELIMITER //

CREATE TRIGGER chk_fecha_futura_insert
BEFORE INSERT ON empleados
FOR EACH ROW
BEGIN
    IF NEW.fecha_contratacion > CURRENT_DATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La fecha de contratación no puede ser futura.';
    END IF;
END //

-- 5. Trigger para evitar fechas futuras al ACTUALIZAR
CREATE TRIGGER chk_fecha_futura_update
BEFORE UPDATE ON empleados
FOR EACH ROW
BEGIN
    IF NEW.fecha_contratacion > CURRENT_DATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La fecha de contratación no puede ser futura.';
    END IF;
END //

DELIMITER ;