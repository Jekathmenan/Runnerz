CREATE TABLE IF NOT EXISTS Run (
    id SERIAL  PRIMARY KEY,
    title VARCHAR(250),
    started_on TIMESTAMP NOT NULL,
    completed_on TIMESTAMP NOT NULL,
    miles INT NOT NULL,
    location VARCHAR(10),
    version INT
);