-- =====================================================
-- BASE DE DATOS: AERONAUTICA_DB
-- Proyecto: Aeronautica Maintenance System
-- Compatible con MySQL 8 / XAMPP
-- =====================================================

-- Eliminar base de datos si existe (opcional)
DROP DATABASE IF EXISTS aeronautica_db;

-- Crear base de datos
CREATE DATABASE aeronautica_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

-- Usar la base de datos
USE aeronautica_db;

-- =====================================================
-- TABLA: USUARIOS
-- Roles: ADMIN | MECANICO | PILOTO
-- =====================================================
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'MECANICO', 'PILOTO') NOT NULL
);

-- Usuarios de ejemplo
INSERT INTO usuarios (username, password, rol) VALUES
('admin', '1234', 'ADMIN'),
('mecanico1', '1234', 'MECANICO'),
('piloto1', '1234', 'PILOTO');

-- =====================================================
-- TABLA: AERONAVES
-- =====================================================
CREATE TABLE aeronaves (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    modelo VARCHAR(100),
    estado VARCHAR(50)
);

-- Datos de ejemplo
INSERT INTO aeronaves (matricula, modelo, estado) VALUES
('EC-ABC', 'Airbus A320', 'Operativa'),
('EC-XYZ', 'Boeing 737', 'En mantenimiento'),
('EC-DEF', 'Cessna 172', 'Operativa');

-- =====================================================
-- TABLA: MECANICOS
-- =====================================================
CREATE TABLE mecanicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100)
);

-- Datos de ejemplo
INSERT INTO mecanicos (nombre, especialidad) VALUES
('Juan Pérez', 'Motores'),
('Laura Gómez', 'Aviónica');

-- =====================================================
-- TABLA: PIEZAS
-- =====================================================
CREATE TABLE piezas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    stock INT NOT NULL
);

-- Datos de ejemplo
INSERT INTO piezas (nombre, descripcion, stock) VALUES
('Filtro de aceite', 'Filtro para motor', 15),
('Bujía', 'Bujía de encendido', 40);

-- =====================================================
-- TABLA: REVISIONES
-- Relación con aeronaves y mecánicos
-- =====================================================
CREATE TABLE revisiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    descripcion VARCHAR(255),
    aeronave_id INT,
    mecanico_id INT,
    CONSTRAINT fk_revision_aeronave
        FOREIGN KEY (aeronave_id) REFERENCES aeronaves(id),
    CONSTRAINT fk_revision_mecanico
        FOREIGN KEY (mecanico_id) REFERENCES mecanicos(id)
);

-- Datos de ejemplo
INSERT INTO revisiones (fecha, descripcion, aeronave_id, mecanico_id) VALUES
('2025-01-10', 'Revisión general', 1, 1),
('2025-01-12', 'Cambio de filtro', 2, 2);

