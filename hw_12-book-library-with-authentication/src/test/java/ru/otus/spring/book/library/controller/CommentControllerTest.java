package ru.otus.spring.book.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.CommentService;
import ru.otus.spring.book.library.service.LibraryUserDetailsServiceImpl;

import javax.annotation.PostConstruct;

import static org.mockito.BDDMockito.given;
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
    @MockBean(name = "libraryUserDetailsService")
    private LibraryUserDetailsServiceImpl libraryUserDetailsServiceImpl;

    @PostConstruct
    void setUp() {
        String username = "test_user";
        String password = "test_user123aZ";
        String role = "TEST";
        UserDetails userDetails = User.withUsername(username)
                .password(password)
                .roles(role)
                .build();
        given(libraryUserDetailsServiceImpl.loadUserByUsername(username)).willReturn(userDetails);
    }


    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlComments() throws Exception {
        mockMvc.perform(get(URL_COMMENTS + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlDeleteComment() throws Exception {
        mockMvc.perform(post(URL_DELETE_COMMENT + "?" + PARAM_COMMENT_ID + "=123").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlAddComment() throws Exception {
        mockMvc.perform(get(URL_ADD_COMMENT + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlSaveComment() throws Exception {
        mockMvc.perform(post(URL_SAVE_COMMENT + "?" + PARAM_BOOK_ID  + "=123&text=\"Any text\"").with(csrf()))
                .andExpect(status().isFound());
    }

}
