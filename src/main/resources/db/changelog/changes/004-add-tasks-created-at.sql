--liquibase formatted sql

--changeset vseslav:004-add-tasks-created-at
ALTER TABLE tasks
    ADD COLUMN created_at DATE NOT NULL DEFAULT CURRENT_DATE;
