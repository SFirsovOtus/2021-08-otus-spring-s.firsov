package ru.otus.spring.book.library.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.library.service.BookService;

@Component
@RequiredArgsConstructor
public class ThereAreBooksHealthIndicator implements HealthIndicator {

    private final BookService bookService;


    @Override
    public Health health() {
        int bookCount = bookService.countAll();

        if (bookCount == 0) {
            return Health.down()
                    .withDetail("bookCount", bookCount)
                    .build();
        } else {
            return Health.up()
                    .withDetail("bookCount", bookCount)
                    .build();
        }

    }

}
