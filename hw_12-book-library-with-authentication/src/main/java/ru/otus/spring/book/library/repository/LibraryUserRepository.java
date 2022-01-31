package ru.otus.spring.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.library.domain.security.LibraryUser;

import java.util.Optional;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, String> {

    Optional<LibraryUser> findByUsername(String username);

}
