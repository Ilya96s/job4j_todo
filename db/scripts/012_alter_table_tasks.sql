ALTER TABLE tasks DROP COLUMN created;
ALTER TABLE tasks ADD COLUMN created TIMESTAMP WITHOUT TIME ZONE DEFAULT now();

comment on column tasks.created is 'Абсолютное время создания без часового пояса';