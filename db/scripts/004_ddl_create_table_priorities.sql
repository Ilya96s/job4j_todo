CREATE TABLE priorities (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    position INT
);

comment on table priorities is 'Таблица с приоритетами';
comment on column priorities.id is 'Идентификатор приоритета';
comment on column priorities.name is 'Навзание приоритета';
comment on column priorities.position is 'Номер приоритета';