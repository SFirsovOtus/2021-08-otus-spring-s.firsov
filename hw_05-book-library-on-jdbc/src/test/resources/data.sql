insert into authors (id, name) values (1, 'Братья Стругацкие');
insert into authors (id, name) values (2, 'Эндрю Таненбаум');
insert into authors (id, name) values (3, 'В. И. Ленин');

insert into genres (id, name) values (1, 'Фантастика');
insert into genres (id, name) values (2, 'Философия');
insert into genres (id, name) values (3, 'Компьютерная литература');

insert into books (id, name, author_id, genre_id) values (1, 'Пикник на обочине', 1, 1);
insert into books (id, name, author_id, genre_id) values (2, 'Жук в муравейнике', 1, 1);
insert into books (id, name, author_id, genre_id) values (3, 'Современные операционные системы', 2, 3);
