CREATE TABLE IF NOT exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  address VARCHAR,
  email VARCHAR NOT NULL
);

INSERT INTO users VALUES(default, 'User#1', 'Kanalveien 52c', 'mio@mail.com');