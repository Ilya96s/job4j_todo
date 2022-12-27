CREATE TABLE IF NOT EXISTS tasks_categories (
    id SERIAL PRIMARY KEY,
    task_id INT REFERENCES tasks(id),
    category_id INT REFERENCES categories(id)
);

comment on table tasks_categories is 'Таблица связей задач и категорий';
comment on column tasks_categories.id is 'Идентификатор записи';
comment on column tasks_categories.task_id is 'Идентификатор задачи';
comment on column tasks_categories.category_id is 'Идентификатор категории';
