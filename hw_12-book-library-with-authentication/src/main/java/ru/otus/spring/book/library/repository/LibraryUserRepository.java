package ru.otus.spring.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.library.domain.LibraryUser;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {



}
