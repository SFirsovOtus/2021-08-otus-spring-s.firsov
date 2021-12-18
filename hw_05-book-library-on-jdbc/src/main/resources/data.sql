insert into authors (name) values ('Братья Стругацкие');
insert into authors (name) values ('Эндрю Таненбаум');
insert into authors (name) values ('В. И. Ленин');
insert into authors (name) values ('Герберт Уэллс');
insert into authors (name) values ('Брайан Керниган');
insert into authors (name) values ('Иммануил Кант');

insert into genres (name) values ('Фантастика');
insert into genres (name) values ('Философия');
insert into genres (name) values ('Компьютерная литература');

insert into books (name, author_id, genre_id) values ('Пикник на обочине', 1, 1);
insert into books (name, author_id, genre_id) values ('Жук в муравейнике', 1, 1);
insert into books (name, author_id, genre_id) values ('Современные операционные системы', 2, 3);
insert into books (name, author_id, genre_id) values ('Компьютерные сети', 2, 3);
insert into books (name, author_id, genre_id) values ('Философские тетради', 3, 2);
insert into books (name, author_id, genre_id) values ('Материализм и эмпириокритицизм', 3, 2);
insert into books (name, author_id, genre_id) values ('Человек-невидимка', 4, 1);
insert into books (name, author_id, genre_id) values ('Война миров', 4, 1);
insert into books (name, author_id, genre_id) values ('Язык программирования C', 5, 3);
insert into books (name, author_id, genre_id) values ('UNIX. Программное окружение', 5, 3);
insert into books (name, author_id, genre_id) values ('Критика чистого разума', 6, 2);
insert into books (name, author_id, genre_id) values ('Критика практического разума', 6, 2);
