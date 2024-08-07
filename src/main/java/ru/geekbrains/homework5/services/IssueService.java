package ru.geekbrains.homework5.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.homework5.dto.IssueDto;
import ru.geekbrains.homework5.mappers.IssueMapper;
import ru.geekbrains.homework5.model.Issue;
import ru.geekbrains.homework5.repository.BookRepository;
import ru.geekbrains.homework5.repository.IssueRepository;
import ru.geekbrains.homework5.repository.ReaderRepository;

import javax.naming.NoPermissionException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueService {

    @Value("${application.max-count-book:1}")
    private int value;

    public static final String NOT_POSSIBLE_ISSUE_MESSAGE = "Количество выданных книг достигло предела у читателя с id=";
    public static final String NOT_FOUND_ISSUE_MESSAGE = "Не удалось найти книгу с id=";

    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;

    /**
     * Create new Issue
     * @param request
     * @return
     * @throws NoPermissionException
     */
    @Transactional
    public IssueDto createIssue(IssueDto request) throws NoPermissionException {

        if (bookRepository.findById(request.getBookId()).isEmpty()) {
            log.info(BookService.NOT_FOUND_BOOK_MESSAGE + request.getBookId());
            throw new NoSuchElementException(BookService.NOT_FOUND_BOOK_MESSAGE + request.getBookId());
        }
        if (readerRepository.findById(request.getReaderId()).isEmpty()) {
            log.info(ReaderService.NOT_FOUND_READER_MESSAGE + request.getReaderId());
            throw new NoSuchElementException(ReaderService.NOT_FOUND_READER_MESSAGE + request.getReaderId());
        }

        if (issueRepository.findAll().stream().
                filter(x -> x.getIdReader() == request.getReaderId() && x.getReturned_at() == null).count() >= value) {
            log.info(NOT_POSSIBLE_ISSUE_MESSAGE + request.getReaderId());
            throw new NoPermissionException(NOT_POSSIBLE_ISSUE_MESSAGE + request.getReaderId());

        }

        Issue issue = IssueMapper.toIssue(request);
        return IssueMapper.toIssueDto(issueRepository.saveAndFlush(issue));
    }

    /**
     * Close issue
     * @param issueId
     * @return
     */
    @Transactional
    public IssueDto closeIssue(long issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            log.info(NOT_FOUND_ISSUE_MESSAGE + issueId);
            throw new NoSuchElementException(NOT_FOUND_ISSUE_MESSAGE + issueId);
        }
        issue.setReturned_at(LocalDateTime.now());
        return IssueMapper.toIssueDto(issueRepository.saveAndFlush(issue));
    }

    /**
     * Gets issue by id
     * @param id
     * @return
     */
    public IssueDto getById(long id) {
        Issue issue = issueRepository.findById(id).orElse(null);
        Optional<Issue> optionalIssue= issueRepository.findById(id);
        if (issue != null) {
            return IssueMapper.toIssueDto(issue);
        } else {
            log.info("Не зарегистрировано выдачи с id=" +id);
            throw new NoSuchElementException("Не зарегистрировано выдачи с id=" + id);
        }
    }
}
