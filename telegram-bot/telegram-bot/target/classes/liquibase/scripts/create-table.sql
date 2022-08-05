-- liquibase formatted sql

-- changeset sascvotch:1
CREATE TABLE IF NOT EXISTS notification_task
(
    id    BIGSERIAL PRIMARY KEY ,
    id_chat INTEGER NOT NULL,
    task_text  VARCHAR(200) NOT NULL ,
    task_date TIMESTAMP NOT NULL
    );
