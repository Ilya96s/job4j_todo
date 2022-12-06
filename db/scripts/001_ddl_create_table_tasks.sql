CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    description text,
    created TIMESTAMP,
    done BOOLEAN
);

comment on table tasks is 'Таблица с заданиями';
comment on column tasks.id is 'Идентификатор задания';
comment on column tasks.description is 'Описание задания';
comment on column tasks.created is 'Дата создания задания';
comment on column tasks.done is 'Статус задания';
