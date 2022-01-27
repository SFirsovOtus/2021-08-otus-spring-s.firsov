package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.LibraryUser;

import java.util.List;

public interface LibraryUserService {

    List<LibraryUser> findAll();

}
