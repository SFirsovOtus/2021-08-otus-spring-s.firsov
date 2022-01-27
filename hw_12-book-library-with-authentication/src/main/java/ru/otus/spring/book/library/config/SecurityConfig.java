package ru.otus.spring.book.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.otus.spring.book.library.domain.LibraryUser;
import ru.otus.spring.book.library.service.LibraryUserService;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LibraryUserService libraryUserService;


    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        List<LibraryUser> libraryUsers = libraryUserService.findAll();

        List<UserDetails> users = new ArrayList<>();
        libraryUsers.forEach(libraryUser -> users.add(
                User.withUsername(libraryUser.getName())
                        .password(libraryUser.getPassword())
                        .roles(libraryUser.getRole())
                        .build()
        ));

        auth.userDetailsService(new InMemoryUserDetailsManager(users))
                .passwordEncoder(passwordEncoder);
    }

}
