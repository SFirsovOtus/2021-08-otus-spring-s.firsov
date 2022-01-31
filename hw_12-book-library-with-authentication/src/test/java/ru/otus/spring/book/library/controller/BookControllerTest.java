package ru.otus.spring.book.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.library.service.AuthorService;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.GenreService;
import ru.otus.spring.book.library.service.LibraryUserDetailsServiceImpl;

import javax.annotation.PostConstruct;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.spring.book.library.controller.BookController.*;

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
    void userShouldAuthenticateToUrlRootByGet() throws Exception {
        mockMvc.perform(get(URL_ROOT))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlRootByPost() throws Exception {
        mockMvc.perform(post(URL_ROOT).with(csrf()))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlBooks() throws Exception {
        mockMvc.perform(get(URL_BOOKS))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlDeleteBook() throws Exception {
        mockMvc.perform(post(URL_DELETE_BOOK + "?" + PARAM_BOOK_ID + "=123").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlAddBook() throws Exception {
        mockMvc.perform(get(URL_ADD_BOOK))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test_user", userDetailsServiceBeanName = "libraryUserDetailsService")
    void userShouldAuthenticateToUrlSaveBook() throws Exception {
        mockMvc.perform(post(URL_SAVE_BOOK + "?book=\"Any book\"&author=\"Any author\"&genres=\"Any genres\"").with(csrf()))
                .andExpect(status().isFound());
    }

}
