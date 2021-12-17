insert into authors (name) values ('Братья Стругацкие');
insert into authors (name) values ('Эндрю Таненбаум');
insert into authors (name) values ('В. И. Ленин');

insert into genres (name) values ('Фантастика');
insert into genres (name) values ('Философия');
insert into genres (name) values ('Компьютерная литература');

insert into books (name, author_id, genre_id) values ('Пикник на обочине', 1, 1);
insert into books (name, author_id, genre_id) values ('Жук в муравейнике', 1, 1);
insert into books (name, author_id, genre_id) values ('Современные операционные системы', 2, 3);
