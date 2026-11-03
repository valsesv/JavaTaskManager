--liquibase formatted sql

--changeset vseslav:002-categories-per-user
DELETE FROM tasks;
DELETE FROM categories;

ALTER TABLE categories DROP CONSTRAINT IF EXISTS categories_name_key;

ALTER TABLE categories
    ADD COLUMN user_id BIGINT NOT NULL REFERENCES users (id);

CREATE UNIQUE INDEX uk_categories_user_name ON categories (user_id, name);
