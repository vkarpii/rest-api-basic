DROP
USER IF EXISTS 'server'@'localhost';
CREATE
USER 'server'@'localhost' IDENTIFIED BY 'y7FQbB%ATUY8bK3SBskAX7efCN@ZMaw#8*k*#I9!(fWH';
GRANT ALL PRIVILEGES ON certificates_system_db.* TO
'server'@'localhost';
FLUSH
PRIVILEGES;