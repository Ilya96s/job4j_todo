CREATE TABLE IF NOT EXISTS todo_user(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

comment on table todo_user is 'Таблица с пользователями';
comment on column todo_user.id is 'Имя пользователя';
comment on column todo_user.login is 'Логин пользователя';
comment on column todo_user.password is 'Пароль пользователя';