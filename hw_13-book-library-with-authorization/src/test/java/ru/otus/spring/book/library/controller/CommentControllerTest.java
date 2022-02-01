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
import static ru.otus.spring.book.library.config.SecurityConfig.*;
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

    private static final String usernameOfAdmin = "admin";
    private final UserDetails userDetailsOfAdmin = User.withUsername(usernameOfAdmin)
            .password("admin123")
            .roles(ROLE_ADMIN)
            .build();

    private static final String usernameOfLibrarian = "librarian";
    private final UserDetails userDetailsOfLibrarian = User.withUsername(usernameOfLibrarian)
            .password("librarian123")
            .roles(ROLE_LIBRARIAN)
            .build();

    private static final String usernameOfReader = "reader";
    private final UserDetails userDetailsOfReader = User.withUsername(usernameOfReader)
            .password("reader123")
            .roles(ROLE_READER, ROLE_REVIEWER)
            .build();

    private static final String usernameOfGuest = "guest";
    private final UserDetails userDetailsOfGuest = User.withUsername(usernameOfGuest)
            .password("guest123")
            .roles(ROLE_GUEST)
            .build();

    @PostConstruct
    void setUp() {
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfAdmin)).willReturn(userDetailsOfAdmin);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfLibrarian)).willReturn(userDetailsOfLibrarian);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfReader)).willReturn(userDetailsOfReader);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfGuest)).willReturn(userDetailsOfGuest);
    }


    @Test
    @WithUserDetails(value = usernameOfAdmin, userDetailsServiceBeanName = "libraryUserDetailsService")
    void adminShouldAuthorizeToUrlComments() throws Exception {
        mockMvc.perform(get(URL_COMMENTS + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = usernameOfLibrarian, userDetailsServiceBeanName = "libraryUserDetailsService")
    void librarianShouldAuthorizeToUrlComments() throws Exception {
        mockMvc.perform(get(URL_COMMENTS + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = usernameOfReader, userDetailsServiceBeanName = "libraryUserDetailsService")
    void readerShouldAuthorizeToUrlComments() throws Exception {
        mockMvc.perform(get(URL_COMMENTS + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = usernameOfGuest, userDetailsServiceBeanName = "libraryUserDetailsService")
    void guestShouldAuthorizeToUrlComments() throws Exception {
        mockMvc.perform(get(URL_COMMENTS + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails(value = usernameOfLibrarian, userDetailsServiceBeanName = "libraryUserDetailsService")
    void librarianShouldAuthorizeToUrlDeleteComment() throws Exception {
        mockMvc.perform(post(URL_DELETE_COMMENT + "?" + PARAM_COMMENT_ID + "=123").with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails(value = usernameOfReader, userDetailsServiceBeanName = "libraryUserDetailsService")
    void readerShouldAuthorizeToUrlAddComment() throws Exception {
        mockMvc.perform(get(URL_ADD_COMMENT + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = usernameOfAdmin, userDetailsServiceBeanName = "libraryUserDetailsService")
    void adminShouldAuthorizeToUrlAddComment() throws Exception {
        mockMvc.perform(get(URL_ADD_COMMENT + "?" + PARAM_BOOK_ID + "=123"))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails(value = usernameOfReader, userDetailsServiceBeanName = "libraryUserDetailsService")
    void readerShouldAuthorizeToUrlSaveComment() throws Exception {
        mockMvc.perform(post(URL_SAVE_COMMENT + "?" + PARAM_BOOK_ID  + "=123&text=\"Any text\"").with(csrf()))
                .andExpect(status().isFound());
    }

}
