package ru.otus.spring.book.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.book.library.service.LibraryUserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
