package ru.geekbrains.homework5.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.homework5.services.UiService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("ui")
@RequiredArgsConstructor
public class UiController {
    private final UiService uiService;

    /**
     * Gets all books
     * @param model
     * @return
     */
    @GetMapping("books")
    public  String getAllBooks(Model model) {

        model.addAttribute("books", uiService.getAllBook());
        return "books-all";
    }

    /**
     * Gets all readers
     * @param model
     * @return
     */
    @GetMapping("readers")
    public String getAllReaders(Model model) {

        model.addAttribute("readers", uiService.getAllReaders());
        return "readers-all";
    }

    /**
     * Gets all issue reports
     * @param model
     * @return
     */
    @GetMapping("issues")
    public String getAllIssuesReports(Model model) {
        model.addAttribute("reports", uiService.getIssueReport());
        return "issues-all";
    }

    /**
     * Gets book list of reader by id
     * @param model
     * @param readerId
     * @return
     */
    @GetMapping("reader/{readerId}")
    public String getBooksByReaderId(Model model, @PathVariable long readerId ) {
        try {
            model.addAttribute("reader", uiService.getReaderById(readerId));
            model.addAttribute("books", uiService.getBooksByReaderId(readerId));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "reader-books";
    }
}
