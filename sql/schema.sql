-- crea lo que se necesita para la base de datos 
CREATE DATABASE IF NOT EXISTS empresa_db;

USE empresa_db;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,

    nombre_completo VARCHAR(100) NOT NULL,

    departamento VARCHAR(100) NOT NULL,
-- me permite usar nombres algo largos
    salario DECIMAL(10,2) NOT NULL,
-- permite usar 2 decimales
    fecha_contratacion DATE NOT NULL,
-- almacena la fecha de contratacion
    activo BOOLEAN NOT NULL DEFAULT TRUE,
-- permite representar algun empleado como activo o no
    CONSTRAINT chk_salario_positivo
        CHECK (salario > 0),

    CONSTRAINT chk_fecha_contratacion
        CHECK (fecha_contratacion <= CURRENT_DATE)
);