-- Sample towers
INSERT INTO towers (location, city, region, status, last_updated) VALUES
('Sector 15', 'New Delhi', 'North', 'ACTIVE', CURRENT_TIMESTAMP),
('Andheri', 'Mumbai', 'West', 'ERROR', CURRENT_TIMESTAMP),
('Rajaji Nagar', 'Bengaluru', 'South', 'UNDER_MAINTENANCE', CURRENT_TIMESTAMP);

-- Sample technicians
INSERT INTO technicians (name, phone, region) VALUES
('Ravi Kumar', '9876543210', 'North'),
('Sunita Sharma', '9123456780', 'South');

-- Sample error reports
INSERT INTO error_reports (tower_id, error_type, description, priority, reported_time, status) VALUES
(2, 'Power Failure', 'Tower lost mains power', 'HIGH', CURRENT_TIMESTAMP, 'OPEN');

-- Sample assignments
INSERT INTO assignments (tower_id, tech_id, eta_hours, status, assigned_time) VALUES
(2, 2, 4, 'ASSIGNED', CURRENT_TIMESTAMP);
