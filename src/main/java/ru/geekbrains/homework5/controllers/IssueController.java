package ru.geekbrains.homework5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.homework5.dto.IssueDto;
import ru.geekbrains.homework5.model.Issue;
import ru.geekbrains.homework5.services.IssueService;

import javax.naming.NoPermissionException;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("issue")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    /**
     * Creates new issue
     * @param issueRequest
     * @return
     */
    @Operation(summary = "creates new issue", description = "Создаёт новый запрос на выдачу книги читателю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<IssueDto> issueBook(@RequestBody IssueDto issueRequest) {
        log.info("Поступил запрос на выдачу: readerId={}, bookId={}",
                issueRequest.getReaderId(),
                issueRequest.getBookId());

        try {
            return new ResponseEntity<>(issueService.createIssue(issueRequest), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NoPermissionException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

        }
    }

    /**
     * Gets issue by id
     * @param id
     * @return
     */
    @Operation(summary = "get issue by id", description = "Загружает выдачу по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public ResponseEntity<IssueDto> getById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(issueService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Closes issue by id
     * @param issueId
     * @return
     */
    @Operation(summary = "close issue by id",
            description = "Обновляет выдачу с указанным идентификатотом, отмечая время возвврата книги")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{issueId}")
    public ResponseEntity<IssueDto> closeById(@PathVariable long issueId) {
        try {
            return new ResponseEntity<>(issueService.closeIssue(issueId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}
