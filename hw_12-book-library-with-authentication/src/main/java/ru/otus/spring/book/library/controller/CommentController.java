package ru.otus.spring.book.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.CommentService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.book.library.controller.BookController.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final BookService bookService;
    private final CommentService commentService;


    public static final String URL_COMMENTS = "/comments";
    public static final String URL_DELETE_COMMENT = "/delete-comment";
    public static final String URL_ADD_COMMENT = "/add-comment";
    public static final String URL_SAVE_COMMENT = "/save-comment";

    public static final String PARAM_COMMENT_ID = "comment_id";


    @GetMapping(URL_COMMENTS)
    public String getComments(@RequestParam(PARAM_BOOK_ID) long bookId, Model model) {
        Book book = bookService.findById(bookId).orElse(null);
        model.addAttribute("url_books", URL_BOOKS);

        if (book == null) {
            return "no-such-book";
        }

        List<Comment> comments = commentService.findByBook(new Book(bookId, null, null, null)).stream()
                .sorted(Comparator.comparing(Comment::getId))
                .collect(Collectors.toList());

        model.addAttribute("comments", comments);
        model.addAttribute("book", book);
        model.addAttribute("url_delete_comment", URL_DELETE_COMMENT);
        model.addAttribute("param_comment_id", PARAM_COMMENT_ID);
        model.addAttribute("url_add_comment", URL_ADD_COMMENT);
        model.addAttribute("param_book_id", PARAM_BOOK_ID);

        return "comments";
    }

    @PostMapping(URL_DELETE_COMMENT)
    public String deleteComment(@RequestParam(PARAM_COMMENT_ID) long commentId, Model model) {
        Comment comment = commentService.findById(commentId).orElse(null);

        if (comment == null) {
            model.addAttribute("url_books", URL_BOOKS);
            return "no-such-comment";
        }

        commentService.removeById(commentId);

        return "redirect:" + URL_COMMENTS + "?" + PARAM_BOOK_ID + "=" + comment.getBook().getId();
    }

    @GetMapping(URL_ADD_COMMENT)
    public String addComment(@RequestParam(PARAM_BOOK_ID) long bookId, Model model) {
        Comment comment = new Comment(0, null, new Book(bookId, null, null, null));

        model.addAttribute("comment", comment);
        model.addAttribute("url_comments", URL_COMMENTS);
        model.addAttribute("param_book_id", PARAM_BOOK_ID);
        model.addAttribute("url_save_comment", URL_SAVE_COMMENT);

        return "add-comment";
    }

    @PostMapping(URL_SAVE_COMMENT)
    public String saveComment(@RequestParam(PARAM_BOOK_ID) long bookId, Comment comment, Model model) {
        if (comment.getText().trim().isEmpty()) {
            model.addAttribute("book_id", bookId);
            model.addAttribute("url_comments", URL_COMMENTS);
            model.addAttribute("param_book_id", PARAM_BOOK_ID);
            return "need-to-fill-comment";
        }

        Book book = new Book(bookId, null, null, null);

        commentService.add(comment.setBook(book));

        return "redirect:" + URL_COMMENTS + "?" + PARAM_BOOK_ID + "=" + bookId;
    }

}
