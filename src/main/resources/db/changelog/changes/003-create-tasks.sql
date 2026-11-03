--liquibase formatted sql

--changeset vseslav:003-create-tasks
CREATE TABLE tasks (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status      VARCHAR(32) NOT NULL DEFAULT 'TODO',
    user_id     BIGINT REFERENCES users (id),
    category_id BIGINT REFERENCES categories (id)
);
