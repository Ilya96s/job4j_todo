ALTER TABLE todo_user ADD COLUMN user_zone VARCHAR;

comment on column todo_user.user_zone is 'Часовой пояс пользователя';