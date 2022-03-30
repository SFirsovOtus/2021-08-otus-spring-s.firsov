package ru.otus.spring.book.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.service.AuthorService;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.GenreService;
import ru.otus.spring.book.library.service.ToStringService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean(name = "toStringService")
    private ToStringService toStringService;


    @Test
    void getBooksShouldReturnAllBooks() throws Exception {
        List<Book> books = List.of(
                new Book(1, "Name1", new Author(1, "Author1"), List.of(new Genre(1, "Genre1"))),
                new Book(2, "Name2", new Author(1, "Author2"), List.of(new Genre(2, "Genre2")))
        );

        given(bookService.findAll()).willReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(books.get(0).getName())))
                .andExpect(content().string(containsString(books.get(1).getName())));
    }

    @Test
    void deleteBookShouldDeleteBook() throws Exception {
        doNothing().when(bookService).removeById(anyLong());

        mockMvc.perform(post("/delete-book?book_id=123"))
                .andExpect(redirectedUrl("/books"))
                .andExpect(status().isFound());
    }

    @Test
    void addBookShouldReturnPageForBookAddition() throws Exception {
        mockMvc.perform(get("/add-book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Add book")));
    }

    @Test
    void saveBookShouldSaveBook() throws Exception {
        mockMvc.perform(post("/save-book?book=\"Any book\"&author=\"Any author\"&genres=\"Any genres\""))
                .andExpect(redirectedUrl("/books"))
                .andExpect(status().isFound());
    }

}
