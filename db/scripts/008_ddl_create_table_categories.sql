CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

comment on table categories is 'Таблица с категориями';
comment on column categories.id is 'Идентификатор категории';
comment on column categories.name is 'Название категории';