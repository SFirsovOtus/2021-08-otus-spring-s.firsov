package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.LibraryUser;
import ru.otus.spring.book.library.repository.LibraryUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryUserServiceImpl implements LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;


    @Override
    public List<LibraryUser> findAll() {
        return libraryUserRepository.findAll();
    }

}
