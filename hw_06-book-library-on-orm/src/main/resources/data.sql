insert into authors (id, name) values (1, 'Братья Стругацкие');
insert into authors (id, name) values (2, 'Эндрю Таненбаум');
insert into authors (id, name) values (3, 'В. И. Ленин');
insert into authors (id, name) values (4, 'Герберт Уэллс');
insert into authors (id, name) values (5, 'Брайан Керниган');
insert into authors (id, name) values (6, 'Иммануил Кант');

insert into books (id, name, author_id) values (1, 'Пикник на обочине', 1);
insert into books (id, name, author_id) values (2, 'Жук в муравейнике', 1);
insert into books (id, name, author_id) values (3, 'Современные операционные системы', 2);
insert into books (id, name, author_id) values (4, 'Компьютерные сети', 2);
insert into books (id, name, author_id) values (5, 'Философские тетради', 3);
insert into books (id, name, author_id) values (6, 'Материализм и эмпириокритицизм', 3);
insert into books (id, name, author_id) values (7, 'Человек-невидимка', 4);
insert into books (id, name, author_id) values (8, 'Война миров', 4);
insert into books (id, name, author_id) values (9, 'Язык программирования C', 5);
insert into books (id, name, author_id) values (10, 'UNIX. Программное окружение', 5);
insert into books (id, name, author_id) values (11, 'Критика чистого разума', 6);
insert into books (id, name, author_id) values (12, 'Критика практического разума', 6);

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
insert into book_genre_links (book_id, genre_id) values (9, 3);
insert into book_genre_links (book_id, genre_id) values (9, 9);
insert into book_genre_links (book_id, genre_id) values (10, 3);
insert into book_genre_links (book_id, genre_id) values (11, 2);
insert into book_genre_links (book_id, genre_id) values (11, 10);
insert into book_genre_links (book_id, genre_id) values (12, 2);
insert into book_genre_links (book_id, genre_id) values (12, 10);

insert into comments (id, book_id, text) values (1, 1, 'Почитав рецензии понял, что немногие читатели распробовали это произведение');
insert into comments (id, book_id, text) values (2, 1, 'Читайте, не пожалеете');
insert into comments (id, book_id, text) values (3, 2, 'Обожаю эту книгу');
insert into comments (id, book_id, text) values (4, 4, 'Книга написана на понятном для читателя языке');
insert into comments (id, book_id, text) values (5, 4, 'Легко читается');
insert into comments (id, book_id, text) values (6, 4, 'Очень хорошо и обстоятельно написана');
insert into comments (id, book_id, text) values (7, 5, 'Прекрасный сборник конспектов Ильича');
insert into comments (id, book_id, text) values (8, 7, 'Автор хорошо раскрывает характер героя');
insert into comments (id, book_id, text) values (9, 7, 'Интересно было читать в детстве и перечитать с удовольствием сейчас');
insert into comments (id, book_id, text) values (10, 7, 'Прекрасная книга, мне очень понравилось');
insert into comments (id, book_id, text) values (11, 8, 'Очень понравилось, сюжет необычен');
insert into comments (id, book_id, text) values (12, 9, 'Лаконичная, доступная, идеальная для новичков');
insert into comments (id, book_id, text) values (13, 12, 'Решила почитать известный труд известного философа, но не поняла ровным счётом ничего');
insert into comments (id, book_id, text) values (14, 12, 'Пробираться сквозь понятия было трудно без предварительного прочтения "Критики чистого разума"');
