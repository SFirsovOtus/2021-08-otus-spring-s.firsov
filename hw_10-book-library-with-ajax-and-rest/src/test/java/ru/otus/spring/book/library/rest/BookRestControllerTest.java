package ru.otus.spring.book.library.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.ToStringService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.otus.spring.book.library.rest.BookRestController.URI_BOOKS;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean(name = "toStringService")
    private ToStringService toStringService;


    @Test
    void getAllBooksShouldReturnAllBooks() throws Exception {
        List<Book> books = List.of(
                new Book(1, "Name1", new Author(1, "Author1"), List.of(new Genre(1, "Genre1"))),
                new Book(2, "Name2", new Author(1, "Author2"), List.of(new Genre(2, "Genre2")))
        );

        given(bookService.findAll()).willReturn(books);

        mockMvc.perform(get(URI_BOOKS))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"authorDto\":")))
                .andExpect(content().string(containsString("\"genreDtos\":")));
    }

}
