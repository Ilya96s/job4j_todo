CREATE TABLE IF NOT EXISTS todo_user(
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL,
    login varchar NOT NULL UNIQUE,
    password varchar NOT NULL
);

comment on table todo_user is 'Таблица с пользователями';
comment on column todo_user.id is 'Имя пользователя';
comment on column todo_user.login is 'Логин пользователя';
comment on column todo_user.password is 'Пароль пользователя';