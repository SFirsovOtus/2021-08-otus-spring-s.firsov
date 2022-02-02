package ru.otus.spring.book.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

    public static final String URL_ACCESS_DENIED = "/access-denied";


    @RequestMapping(URL_ACCESS_DENIED)
    public String getErrorPage() {
        return "access-denied";
    }

}
