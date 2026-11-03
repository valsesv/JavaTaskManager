--liquibase formatted sql

--changeset vseslav:001-create-users
CREATE TABLE users (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
