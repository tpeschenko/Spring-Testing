package ru.geekbrains.homework5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.homework5.dto.BookDto;
import ru.geekbrains.homework5.model.Book;
import ru.geekbrains.homework5.services.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {

    private final BookService bookService;

    /**
     * Gets all books
     * @return List
     */
    @Operation(summary = "get all books", description = "Загружает все книги из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAll());
    }

    /**
     * Gets book by id
     * @param id
     * @return
     */
    @Operation(summary = "get book by id", description = "Загружает книгу по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public ResponseEntity<BookDto> getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(bookService.getById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Removes book by id
     * @param id
     * @return
     */
    @Operation(summary = "removes book by id", description = "Удаляет книгу с указанным идентификатором из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<BookDto> removeBookById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(bookService.removeById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Created new book
     * @param bookDto
     * @return
     */
    @Operation(summary = "creates new book", description = "Создаёт в базе данных запись с новой книгой")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }

}
