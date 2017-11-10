CREATE DATABASE IF NOT EXISTS hadoopguide;
USE hadoopguide;
GRANT ALL PRIVILEGES ON hadoopguide.* TO 'root'@'localhost';
CREATE TABLE IF NOT EXISTS widgets (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, widget_name VARCHAR(64) NOT NULL, price DECIMAL(10,2), design_date DATE, version INT, design_comment VARCHAR(100));
