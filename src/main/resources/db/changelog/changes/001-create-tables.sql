--liquibase formatted sql

--changeset vseslav:001-create-tables
CREATE TABLE users (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tasks (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status      VARCHAR(32) NOT NULL DEFAULT 'TODO',
    user_id     BIGINT REFERENCES users (id),
    category_id BIGINT REFERENCES categories (id)
);
