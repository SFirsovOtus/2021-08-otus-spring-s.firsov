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
import static ru.otus.spring.book.library.config.SecurityConfig.ROLE_ADMIN;
import static ru.otus.spring.book.library.config.SecurityConfig.ROLE_GUEST;
import static ru.otus.spring.book.library.config.SecurityConfig.ROLE_LIBRARIAN;
import static ru.otus.spring.book.library.config.SecurityConfig.ROLE_READER;
import static ru.otus.spring.book.library.config.SecurityConfig.ROLE_REVIEWER;
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

    private static final String usernameOfAnyUser = "any_user";
    private final UserDetails userDetailsOfAnyUser = User.withUsername(usernameOfAnyUser)
            .password("any_user123")
            .roles("ANY")
            .build();

    @PostConstruct
    void setUp() {
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfAdmin)).willReturn(userDetailsOfAdmin);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfLibrarian)).willReturn(userDetailsOfLibrarian);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfReader)).willReturn(userDetailsOfReader);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfGuest)).willReturn(userDetailsOfGuest);
        given(libraryUserDetailsServiceImpl.loadUserByUsername(usernameOfAnyUser)).willReturn(userDetailsOfAnyUser);
    }


    @Test
    @WithUserDetails(value = usernameOfAnyUser, userDetailsServiceBeanName = "libraryUserDetailsService")
    void anyUserShouldAuthorizeToUrlBooks() throws Exception {
        mockMvc.perform(get(URL_BOOKS))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails(value = usernameOfLibrarian, userDetailsServiceBeanName = "libraryUserDetailsService")
    void librarianShouldAuthorizeToUrlDeleteBook() throws Exception {
        mockMvc.perform(post(URL_DELETE_BOOK + "?" + PARAM_BOOK_ID + "=123").with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails(value = usernameOfLibrarian, userDetailsServiceBeanName = "libraryUserDetailsService")
    void librarianShouldAuthorizeToUrlAddBook() throws Exception {
        mockMvc.perform(get(URL_ADD_BOOK))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = usernameOfAdmin, userDetailsServiceBeanName = "libraryUserDetailsService")
    void adminShouldAuthorizeToUrlAddBook() throws Exception {
        mockMvc.perform(get(URL_ADD_BOOK))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails(value = usernameOfLibrarian, userDetailsServiceBeanName = "libraryUserDetailsService")
    void librarianShouldAuthorizeToUrlSaveBook() throws Exception {
        mockMvc.perform(post(URL_SAVE_BOOK + "?book=\"Any book\"&author=\"Any author\"&genres=\"Any genres\"").with(csrf()))
                .andExpect(status().isFound());
    }

}
