--liquibase formatted sql

--changeset vseslav:002-create-categories
CREATE TABLE categories (
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users (id),
    UNIQUE (user_id, name)
);
