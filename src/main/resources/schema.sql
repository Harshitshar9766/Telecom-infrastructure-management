-- DDL for MySQL/H2. Tables will be created automatically by JPA, but
-- these statements can be used manually if needed.

CREATE TABLE IF NOT EXISTS towers (
    tower_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255),
    city VARCHAR(255),
    region VARCHAR(255),
    status VARCHAR(50),
    last_updated TIMESTAMP
);

CREATE TABLE IF NOT EXISTS error_reports (
    error_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tower_id BIGINT,
    error_type VARCHAR(255),
    description VARCHAR(1024),
    priority VARCHAR(50),
    reported_time TIMESTAMP,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS technicians (
    tech_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    region VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS assignments (
    assignment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tower_id BIGINT,
    tech_id BIGINT,
    eta_hours INT,
    status VARCHAR(50),
    assigned_time TIMESTAMP
);
