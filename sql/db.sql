CREATE DATABASE meganand;
\c meganand;

CREATE TABLE IF NOT EXISTS addresses (
    id      BIGSERIAL
            PRIMARY KEY,
    name    VARCHAR(50)
            NOT NULL,
    line_1  VARCHAR(50)
            NOT NULL,
    line_2  VARCHAR(32),
    city    VARCHAR(32)
            NOT NULL,
    state   VARCHAR(32)
            NOT NULL,
    zip     VARCHAR(10)
            NOT NULL
);