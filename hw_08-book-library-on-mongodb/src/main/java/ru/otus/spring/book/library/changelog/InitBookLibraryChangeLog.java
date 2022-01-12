package ru.otus.spring.book.library.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.repository.BookRepository;
import ru.otus.spring.book.library.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitBookLibraryChangeLog {

    private Book book01, book02, book03, book04,
                 book05, book06, book07, book08,
                 book09, book10, book11, book12;

    @ChangeSet(order = "000", id = "dropDB", author = "DataFiller", runAlways = true)
    public void dropDB(MongoDatabase mongoDatabase){
        mongoDatabase.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "DataFiller", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        book01 = bookRepository.save(new Book("Пикник на обочине", "Братья Стругацкие", List.of("Фантастика", "Роман")));
        book02 = bookRepository.save(new Book("Жук в муравейнике", "Братья Стругацкие", List.of("Фантастика", "Детектив")));
        book03 = bookRepository.save(new Book("Современные операционные системы", "Эндрю Таненбаум", List.of("Компьютерная литература")));
        book04 = bookRepository.save(new Book("Компьютерные сети", "Эндрю Таненбаум", List.of("Компьютерная литература")));
        book05 = bookRepository.save(new Book("Философские тетради", "В. И. Ленин", List.of("Философия", "Биографии и мемуары")));
        book06 = bookRepository.save(new Book("Материализм и эмпириокритицизм", "В. И. Ленин", List.of("Философия", "Марксизм")));
        book07 = bookRepository.save(new Book("Человек-невидимка", "Герберт Уэллс", List.of("Фантастика", "Роман", "Литература ужасов")));
        book08 = bookRepository.save(new Book("Война миров", "Герберт Уэллс", List.of("Фантастика", "Роман")));
        book09 = bookRepository.save(new Book("Язык программирования C", "Брайан Керниган", List.of("Компьютерная литература", "Программирование")));
        book10 = bookRepository.save(new Book("UNIX. Программное окружение", "Брайан Керниган", List.of("Компьютерная литература")));
        book11 = bookRepository.save(new Book("Критика чистого разума", "Иммануил Кант", List.of("Философия", "Трактат")));
        book12 = bookRepository.save(new Book("Критика практического разума", "Иммануил Кант", List.of("Философия", "Трактат")));
    }

    @ChangeSet(order = "002", id = "initComments", author = "DataFiller", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Почитав рецензии понял, что немногие читатели распробовали это произведение", book01));
        comments.add(new Comment("Читайте, не пожалеете", book01));
        comments.add(new Comment("Обожаю эту книгу", book02));
        comments.add(new Comment("Книга написана на понятном для читателя языке", book04));
        comments.add(new Comment("Легко читается", book04));
        comments.add(new Comment("Очень хорошо и обстоятельно написана", book04));
        comments.add(new Comment("Прекрасный сборник конспектов Ильича", book05));
        comments.add(new Comment("Автор хорошо раскрывает характер героя", book07));
        comments.add(new Comment("Интересно было читать в детстве и перечитать с удовольствием сейчас", book07));
        comments.add(new Comment("Прекрасная книга, мне очень понравилось", book07));
        comments.add(new Comment("Очень понравилось, сюжет необычен", book08));
        comments.add(new Comment("Лаконичная, доступная, идеальная для новичков", book09));
        comments.add(new Comment("Решила почитать известный труд известного философа, но не поняла ровным счётом ничего", book12));
        comments.add(new Comment("Пробираться сквозь понятия было трудно без предварительного прочтения \"Критики чистого разума\"", book12));

        commentRepository.saveAll(comments);
    }

}
