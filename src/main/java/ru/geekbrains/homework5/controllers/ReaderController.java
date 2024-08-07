package ru.geekbrains.homework5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.homework5.dto.IssueDto;
import ru.geekbrains.homework5.dto.ReaderDto;
import ru.geekbrains.homework5.model.Issue;
import ru.geekbrains.homework5.model.Reader;
import ru.geekbrains.homework5.services.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @Operation(summary = "get all readers", description = "Загружает имена всех пользователей системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ReaderDto>> getAll() {
        return new ResponseEntity<>(readerService.getAll(), HttpStatus.OK);
    }

    /**
     * Gets reader by id
     * @param id
     * @return
     */
    @Operation(summary = "get reader by id", description = "Загружает имя пользователя по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public ResponseEntity<ReaderDto> getById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(readerService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Removes reader by id
     * @param id
     * @return
     */
    @Operation(summary = "removes reader by id", description = "Удаляет читателя с указанным идентификатором из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<ReaderDto> removeReaderById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(readerService.removeById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Created new Reader
     * @param readerRequest
     * @return
     */
    @Operation(summary = "creates new reader", description = "Создаёт в базу данных нового читателя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ReaderDto> createReader(@RequestBody ReaderDto readerRequest) {
        return new ResponseEntity<>(readerService.createReader(readerRequest), HttpStatus.CREATED);
    }

    /**
     * Gets issue list by readerId
     * @param readerId
     * @return
     */
    @Operation(summary = "gets all reader issues ",
            description = "Загражает список незакрытых выдач читателя с указанным идентификатором ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{readerId}/issue")
    public ResponseEntity<List<IssueDto>> getIssuesByReaderId(@PathVariable long readerId) {
        try {
            return new ResponseEntity<>(readerService.getIssues(readerId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
