package ru.otus.spring.book.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.repository.BookRepository;
import ru.otus.spring.book.library.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookLibraryInitialization implements ApplicationRunner {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;


    public static final String COLLECTION_BOOKS = "books";
    public static final String COLLECTION_COMMENTS = "comments";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        reactiveMongoTemplate.dropCollection(COLLECTION_COMMENTS).block();
        reactiveMongoTemplate.dropCollection(COLLECTION_BOOKS).block();

        Author author1 = new Author("Братья Стругацкие");
        Author author2 = new Author("Эндрю Таненбаум");
        Author author3 = new Author("В. И. Ленин");
        Author author4 = new Author("Герберт Уэллс");
        Author author5 = new Author("Брайан Керниган");
        Author author6 = new Author("Иммануил Кант");

        Genre genre01 = new Genre("Фантастика");
        Genre genre02 = new Genre("Философия");
        Genre genre03 = new Genre("Компьютерная литература");
        Genre genre04 = new Genre("Роман");
        Genre genre05 = new Genre("Литература ужасов");
        Genre genre06 = new Genre("Детектив");
        Genre genre07 = new Genre("Биографии и мемуары");
        Genre genre08 = new Genre("Марксизм");
        Genre genre09 = new Genre("Программирование");
        Genre genre10 = new Genre("Трактат");

        Book book01 = bookRepository.save(new Book("Пикник на обочине", author1, List.of(genre01, genre04))).block();
        Book book02 = bookRepository.save(new Book("Жук в муравейнике", author1, List.of(genre01, genre06))).block();
        Book book03 = bookRepository.save(new Book("Современные операционные системы", author2, List.of(genre03))).block();
        Book book04 = bookRepository.save(new Book("Компьютерные сети", author2, List.of(genre03))).block();
        Book book05 = bookRepository.save(new Book("Философские тетради", author3, List.of(genre02, genre07))).block();
        Book book06 = bookRepository.save(new Book("Материализм и эмпириокритицизм", author3, List.of(genre02, genre08))).block();
        Book book07 = bookRepository.save(new Book("Человек-невидимка", author4, List.of(genre01, genre04, genre05))).block();
        Book book08 = bookRepository.save(new Book("Война миров", author4, List.of(genre01, genre04))).block();
        Book book09 = bookRepository.save(new Book("Язык программирования C", author5, List.of(genre03, genre09))).block();
        Book book10 = bookRepository.save(new Book("UNIX. Программное окружение", author5, List.of(genre03))).block();
        Book book11 = bookRepository.save(new Book("Критика чистого разума", author6, List.of(genre02, genre10))).block();
        Book book12 = bookRepository.save(new Book("Критика практического разума", author6, List.of(genre02, genre10))).block();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Почитав рецензии понял, что немногие читатели распробовали это произведение", book01.getId()));
        comments.add(new Comment("Читайте, не пожалеете", book01.getId()));
        comments.add(new Comment("Обожаю эту книгу", book02.getId()));
        comments.add(new Comment("Книга написана на понятном для читателя языке", book04.getId()));
        comments.add(new Comment("Легко читается", book04.getId()));
        comments.add(new Comment("Очень хорошо и обстоятельно написана", book04.getId()));
        comments.add(new Comment("Прекрасный сборник конспектов Ильича", book05.getId()));
        comments.add(new Comment("Автор хорошо раскрывает характер героя", book07.getId()));
        comments.add(new Comment("Интересно было читать в детстве и перечитать с удовольствием сейчас", book07.getId()));
        comments.add(new Comment("Прекрасная книга, мне очень понравилось", book07.getId()));
        comments.add(new Comment("Очень понравилось, сюжет необычен", book08.getId()));
        comments.add(new Comment("Лаконичная, доступная, идеальная для новичков", book09.getId()));
        comments.add(new Comment("Решила почитать известный труд известного философа, но не поняла ровным счётом ничего", book12.getId()));
        comments.add(new Comment("Пробираться сквозь понятия было трудно без предварительного прочтения \"Критики чистого разума\"", book12.getId()));

        commentRepository.saveAll(comments).subscribe();
    }

}
