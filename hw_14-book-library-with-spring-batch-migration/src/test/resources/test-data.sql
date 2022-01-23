insert into authors (id, name) values (2, 'Эндрю Таненбаум');
insert into authors (id, name) values (3, 'В. И. Ленин');
insert into authors (id, name) values (4, 'Герберт Уэллс');

insert into books (id, name, author_id) values (4, 'Компьютерные сети', 2);
insert into books (id, name, author_id) values (5, 'Философские тетради', 3);
insert into books (id, name, author_id) values (7, 'Человек-невидимка', 4);

insert into genres (id, name) values (1, 'Фантастика');
insert into genres (id, name) values (2, 'Философия');
insert into genres (id, name) values (3, 'Компьютерная литература');
insert into genres (id, name) values (4, 'Роман');
insert into genres (id, name) values (5, 'Литература ужасов');
insert into genres (id, name) values (7, 'Биографии и мемуары');

insert into book_genre_links (book_id, genre_id) values (4, 3);
insert into book_genre_links (book_id, genre_id) values (5, 2);
insert into book_genre_links (book_id, genre_id) values (5, 7);
insert into book_genre_links (book_id, genre_id) values (7, 1);
insert into book_genre_links (book_id, genre_id) values (7, 4);
insert into book_genre_links (book_id, genre_id) values (7, 5);
