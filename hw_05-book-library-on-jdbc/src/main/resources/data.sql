insert into authors (id, name) values (1, 'Братья Стругацкие');
insert into authors (id, name) values (2, 'Эндрю Таненбаум');
insert into authors (id, name) values (3, 'В. И. Ленин');
insert into authors (id, name) values (4, 'Герберт Уэллс');
insert into authors (id, name) values (5, 'Брайан Керниган');
insert into authors (id, name) values (6, 'Иммануил Кант');

insert into genres (id, name) values (1, 'Фантастика');
insert into genres (id, name) values (2, 'Философия');
insert into genres (id, name) values (3, 'Компьютерная литература');

insert into books (id, name, author_id, genre_id) values (1, 'Пикник на обочине', 1, 1);
insert into books (id, name, author_id, genre_id) values (2, 'Жук в муравейнике', 1, 1);
insert into books (id, name, author_id, genre_id) values (3, 'Современные операционные системы', 2, 3);
insert into books (id, name, author_id, genre_id) values (4, 'Компьютерные сети', 2, 3);
insert into books (id, name, author_id, genre_id) values (5, 'Философские тетради', 3, 2);
insert into books (id, name, author_id, genre_id) values (6, 'Материализм и эмпириокритицизм', 3, 2);
insert into books (id, name, author_id, genre_id) values (7, 'Человек-невидимка', 4, 1);
insert into books (id, name, author_id, genre_id) values (8, 'Война миров', 4, 1);
insert into books (id, name, author_id, genre_id) values (9, 'Язык программирования C', 5, 3);
insert into books (id, name, author_id, genre_id) values (10, 'UNIX. Программное окружение', 5, 3);
insert into books (id, name, author_id, genre_id) values (11, 'Критика чистого разума', 6, 2);
insert into books (id, name, author_id, genre_id) values (12, 'Критика практического разума', 6, 2);
