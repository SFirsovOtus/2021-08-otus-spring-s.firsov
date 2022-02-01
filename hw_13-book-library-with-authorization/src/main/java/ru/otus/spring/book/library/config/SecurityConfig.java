package ru.otus.spring.book.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.book.library.service.LibraryUserDetailsServiceImpl;

import static ru.otus.spring.book.library.controller.BookController.*;
import static ru.otus.spring.book.library.controller.CommentController.*;
import static ru.otus.spring.book.library.controller.ExceptionController.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_GUEST = "GUEST";
    public static final String ROLE_LIBRARIAN = "LIBRARIAN";
    public static final String ROLE_READER = "READER";
    public static final String ROLE_REVIEWER = "REVIEWER";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(URL_BOOKS).authenticated()
                .and()
                .authorizeRequests().antMatchers(URL_DELETE_BOOK).hasRole(ROLE_LIBRARIAN)
                .and()
                .authorizeRequests().antMatchers(URL_ADD_BOOK).hasAnyRole(ROLE_LIBRARIAN, ROLE_ADMIN)
                .and()
                .authorizeRequests().antMatchers(URL_SAVE_BOOK).hasRole(ROLE_LIBRARIAN)
                .and()
                .authorizeRequests().antMatchers(URL_COMMENTS).hasAnyRole(ROLE_READER, ROLE_LIBRARIAN, ROLE_ADMIN, ROLE_GUEST)
                .and()
                .authorizeRequests().antMatchers(URL_DELETE_COMMENT).hasRole(ROLE_LIBRARIAN)
                .and()
                .authorizeRequests().antMatchers(URL_ADD_COMMENT).hasAnyRole(ROLE_REVIEWER, ROLE_ADMIN)
                .and()
                .authorizeRequests().antMatchers(URL_SAVE_COMMENT).hasRole(ROLE_REVIEWER)
                .and().exceptionHandling().accessDeniedPage(URL_ACCESS_DENIED)
                .and()
                .formLogin();
    }


    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth,
                          PasswordEncoder passwordEncoder,
                          LibraryUserDetailsServiceImpl libraryUserDetailsServiceImpl) throws Exception {
        auth.userDetailsService(libraryUserDetailsServiceImpl)
                .passwordEncoder(passwordEncoder);
    }

}
