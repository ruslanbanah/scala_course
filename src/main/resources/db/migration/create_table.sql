CREATE TABLE IF NOT exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  address VARCHAR,
  email VARCHAR NOT NULL
);