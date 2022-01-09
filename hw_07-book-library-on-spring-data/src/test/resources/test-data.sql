insert into authors (id, name) values (1, 'Братья Стругацкие');
insert into authors (id, name) values (2, 'Эндрю Таненбаум');
insert into authors (id, name) values (3, 'В. И. Ленин');
insert into authors (id, name) values (4, 'Герберт Уэллс');
insert into authors (id, name) values (5, 'Новый автор');

insert into books (id, name, author_id) values (1, 'Пикник на обочине', 1);
insert into books (id, name, author_id) values (2, 'Жук в муравейнике', 1);
insert into books (id, name, author_id) values (3, 'Современные операционные системы', 2);
insert into books (id, name, author_id) values (4, 'Компьютерные сети', 2);
insert into books (id, name, author_id) values (5, 'Философские тетради', 3);
insert into books (id, name, author_id) values (6, 'Материализм и эмпириокритицизм', 3);
insert into books (id, name, author_id) values (7, 'Человек-невидимка', 4);
insert into books (id, name, author_id) values (8, 'Война миров', 4);

insert into genres (id, name) values (1, 'Фантастика');
insert into genres (id, name) values (2, 'Философия');
insert into genres (id, name) values (3, 'Компьютерная литература');
insert into genres (id, name) values (4, 'Роман');
insert into genres (id, name) values (5, 'Литература ужасов');
insert into genres (id, name) values (6, 'Детектив');
insert into genres (id, name) values (7, 'Биографии и мемуары');
insert into genres (id, name) values (8, 'Марксизм');
insert into genres (id, name) values (9, 'Программирование');
insert into genres (id, name) values (10, 'Трактат');
insert into genres (id, name) values (11, 'Новый жанр');

insert into book_genre_links (book_id, genre_id) values (1, 1);
insert into book_genre_links (book_id, genre_id) values (1, 4);
insert into book_genre_links (book_id, genre_id) values (2, 1);
insert into book_genre_links (book_id, genre_id) values (2, 6);
insert into book_genre_links (book_id, genre_id) values (3, 3);
insert into book_genre_links (book_id, genre_id) values (4, 3);
insert into book_genre_links (book_id, genre_id) values (5, 2);
insert into book_genre_links (book_id, genre_id) values (5, 7);
insert into book_genre_links (book_id, genre_id) values (6, 2);
insert into book_genre_links (book_id, genre_id) values (6, 8);
insert into book_genre_links (book_id, genre_id) values (7, 1);
insert into book_genre_links (book_id, genre_id) values (7, 4);
insert into book_genre_links (book_id, genre_id) values (7, 5);
insert into book_genre_links (book_id, genre_id) values (8, 1);
insert into book_genre_links (book_id, genre_id) values (8, 4);

insert into comments (id, book_id, text) values (1, 1, 'Почитав рецензии понял, что немногие читатели распробовали это произведение');
insert into comments (id, book_id, text) values (2, 1, 'Читайте, не пожалеете');
insert into comments (id, book_id, text) values (3, 2, 'Обожаю эту книгу');
insert into comments (id, book_id, text) values (8, 7, 'Автор хорошо раскрывает характер героя');
insert into comments (id, book_id, text) values (9, 7, 'Интересно было читать в детстве и перечитать с удовольствием сейчас');
insert into comments (id, book_id, text) values (10, 7, 'Прекрасная книга, мне очень понравилось');
