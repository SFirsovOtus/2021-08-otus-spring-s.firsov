package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.security.LibraryRole;
import ru.otus.spring.book.library.domain.security.LibraryUser;
import ru.otus.spring.book.library.repository.LibraryUserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsServiceImpl implements UserDetailsService {

    private final LibraryUserRepository libraryUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibraryUser libraryUser = libraryUserRepository.findByUsername(username)
                .orElse(null);

        if (libraryUser == null) {
            throw new UsernameNotFoundException(username);
        }

        String[] roles = libraryUser.getLibraryRoles().stream()
                .map(LibraryRole::getRole)
                .collect(Collectors.toList())
                .toArray(String[]::new);

        return User.withUsername(libraryUser.getUsername())
                .password(libraryUser.getPassword())
                .roles(roles)
                .build();
    }

}
