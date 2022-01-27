package ru.otus.spring.book.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.CommentService;
import ru.otus.spring.book.library.service.LibraryUserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.spring.book.library.controller.BookController.PARAM_BOOK_ID;
import static ru.otus.spring.book.library.controller.CommentController.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private LibraryUserService libraryUserService;


    @Test
    @WithMockUser(username = "some_user", password = "some_password", roles = "SOME_ROLE")
    void userShouldAuthenticateToUrlComments() throws Exception {
        mockMvc.perform(get(URL_COMMENTS + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "some_user", password = "some_password", roles = "SOME_ROLE")
    void userShouldAuthenticateToUrlDeleteComment() throws Exception {
        mockMvc.perform(post(URL_DELETE_COMMENT + "?" + PARAM_COMMENT_ID + "=123").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "some_user", password = "some_password", roles = "SOME_ROLE")
    void userShouldAuthenticateToUrlAddComment() throws Exception {
        mockMvc.perform(get(URL_ADD_COMMENT + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "some_user", password = "some_password", roles = "SOME_ROLE")
    void userShouldAuthenticateToUrlSaveComment() throws Exception {
        mockMvc.perform(post(URL_SAVE_COMMENT + "?" + PARAM_BOOK_ID  + "=123&text=\"Any text\"").with(csrf()))
                .andExpect(status().isFound());
    }

}
