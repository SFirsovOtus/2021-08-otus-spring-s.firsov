package ru.otus.spring.book.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.service.AuthorService;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.CommentService;
import ru.otus.spring.book.library.service.GenreService;
import ru.otus.spring.book.library.service.ToStringService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;
    @MockBean(name = "toStringService")
    private ToStringService toStringService;


    @Test
    void getCommentsShouldReturnNoSuchBookWhenBookNotFound() throws Exception {
        given(bookService.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(get("/comments?book_id=123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No such book")));
    }

    @Test
    void getCommentsShouldReturnCommentsWhenBookFound() throws Exception {
        Book book = new Book(1, "Name1", new Author(1, "Author1"), List.of(new Genre(1, "Genre1")));
        List<Comment> comments = List.of(
                new Comment(1, "Comment1", book),
                new Comment(2, "Comment2", book)
        );
        given(bookService.findById(book.getId())).willReturn(Optional.of(book));
        given(commentService.findByBook(any(Book.class))).willReturn(comments);

        mockMvc.perform(get("/comments?book_id=" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(comments.get(0).getText())));
    }

    @Test
    void deleteCommentShouldReturnNoSuchCommentWhenCommentNotFound() throws Exception {
        given(commentService.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(post("/delete-comment?comment_id=123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No such comment")));
    }

    @Test
    void deleteCommentShouldDeleteCommentWhenCommentFound() throws Exception {
        Comment comment = new Comment(123, "Any comment", null);

        given(commentService.findById(comment.getId())).willReturn(Optional.empty());
        doNothing().when(commentService).removeById(comment.getId());

        mockMvc.perform(post("/delete-comment?comment_id=" + comment.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void addCommentShouldReturnPageForCommentAddition() throws Exception {
        mockMvc.perform(get("/add-comment?book_id=123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Comment text:")));
    }

    @Test
    void saveCommentShouldSaveComment() throws Exception {
        mockMvc.perform(post("/save-comment?book_id=123&text=\"Any text\""))
                .andExpect(redirectedUrl("/comments?book_id=123"))
                .andExpect(status().isFound());
    }

}
