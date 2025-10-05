-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS reevah CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE reevah;

-- Create events table
CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create decoration_types table
CREATE TABLE IF NOT EXISTS decoration_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    display_order INT,
    event_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

-- Create decoration_images table
CREATE TABLE IF NOT EXISTS decoration_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    display_order INT,
    decoration_type_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (decoration_type_id) REFERENCES decoration_types(id) ON DELETE CASCADE
);

-- Insert sample events
INSERT INTO events (name, description, is_active) VALUES
('Wedding', 'Traditional wedding ceremony and reception', TRUE),
('Haldi', 'Pre-wedding haldi ceremony', TRUE),
('Mehandi', 'Pre-wedding mehandi ceremony', TRUE),
('Sangeet', 'Pre-wedding sangeet ceremony', TRUE),
('Cocktail Party', 'Pre-wedding cocktail party', TRUE);

-- Insert sample decoration types for Wedding
INSERT INTO decoration_types (name, description, is_active, display_order, event_id) VALUES
('Mandap Decoration', 'Beautiful mandap decoration for wedding ceremony', TRUE, 1, 1),
('Entrance Gate Decoration', 'Grand entrance gate decoration', TRUE, 2, 1),
('Food Stage Decoration', 'Elegant food stage decoration', TRUE, 3, 1);

-- Insert sample decoration types for other events
INSERT INTO decoration_types (name, description, is_active, display_order, event_id) VALUES
('Haldi Stage Decoration', 'Colorful haldi ceremony decoration', TRUE, 1, 2),
('Mehandi Stage Decoration', 'Traditional mehandi ceremony decoration', TRUE, 1, 3),
('Sangeet Stage Decoration', 'Vibrant sangeet ceremony decoration', TRUE, 1, 4),
('Cocktail Party Decoration', 'Modern cocktail party decoration', TRUE, 1, 5);

-- Insert sample decoration images
INSERT INTO decoration_images (name, image_url, description, is_active, display_order, decoration_type_id) VALUES
-- Mandap Decoration Images
('Traditional Mandap', 'https://images.unsplash.com/photo-1519741497674-611481863552?w=800', 'Beautiful traditional mandap with flowers', TRUE, 1, 1),
('Modern Mandap', 'https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800', 'Contemporary mandap design', TRUE, 2, 1),
('Floral Mandap', 'https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800', 'Mandap decorated with fresh flowers', TRUE, 3, 1),

-- Entrance Gate Decoration Images
('Grand Entrance', 'https://images.unsplash.com/photo-1519741497674-611481863552?w=800', 'Magnificent entrance gate decoration', TRUE, 1, 2),
('Flower Arch', 'https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800', 'Beautiful flower arch entrance', TRUE, 2, 2),

-- Food Stage Decoration Images
('Elegant Food Station', 'https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800', 'Beautifully decorated food station', TRUE, 1, 3),

-- Haldi Decoration Images
('Colorful Haldi Setup', 'https://images.unsplash.com/photo-1519741497674-611481863552?w=800', 'Vibrant haldi ceremony decoration', TRUE, 1, 4),

-- Mehandi Decoration Images
('Traditional Mehandi Setup', 'https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800', 'Traditional mehandi ceremony decoration', TRUE, 1, 5),

-- Sangeet Decoration Images
('Vibrant Sangeet Stage', 'https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800', 'Colorful sangeet ceremony decoration', TRUE, 1, 6),

-- Cocktail Party Decoration Images
('Modern Cocktail Setup', 'https://images.unsplash.com/photo-1519741497674-611481863552?w=800', 'Elegant cocktail party decoration', TRUE, 1, 7);

-- Show the created tables
SHOW TABLES;

-- Show sample data
SELECT 'Events:' as Table_Name;
SELECT * FROM events;

SELECT 'Decoration Types:' as Table_Name;
SELECT dt.*, e.name as event_name FROM decoration_types dt JOIN events e ON dt.event_id = e.id;

SELECT 'Decoration Images:' as Table_Name;
SELECT di.*, dt.name as decoration_type_name FROM decoration_images di JOIN decoration_types dt ON di.decoration_type_id = dt.id;
