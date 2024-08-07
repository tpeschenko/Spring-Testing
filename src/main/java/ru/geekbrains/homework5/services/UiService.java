package ru.geekbrains.homework5.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.homework5.dto.IssueReportDto;
import ru.geekbrains.homework5.mappers.IssueMapper;
import ru.geekbrains.homework5.model.Book;
import ru.geekbrains.homework5.model.Issue;
import ru.geekbrains.homework5.model.Reader;
import ru.geekbrains.homework5.repository.BookRepository;
import ru.geekbrains.homework5.repository.IssueRepository;
import ru.geekbrains.homework5.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class UiService {

    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;

    /**
     * Gets all books
     * @return List books
     */
    public List<Book> getAllBook() {
        Iterable<Book> iterable = bookRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false).toList();
    }

    /**
     * Gets all readers
     * @return List readers
     */
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    /**
     * Gets issue reports for all issue
     * @return
     */
    public List<IssueReportDto> getIssueReport() {
        List<IssueReportDto> reports = new ArrayList<>();

        for (Issue issue: issueRepository.findAll()) {

            if (bookRepository.findById(issue.getIdBook()).isEmpty()) {
            log.info("Не удалось найти книгу с id=" + issue.getIdBook());
            throw new NoSuchElementException("Не удалось найти книгу с id=" + issue.getIdBook());
            }

            if (readerRepository.findById(issue.getIdReader()).isEmpty()) {
            log.info("Не удалось найти читателя с id=" + issue.getIdReader());
            throw new NoSuchElementException("Не удалось найти читателя с id=" + issue.getIdReader());
            }

            reports.add(IssueMapper.toIssueReport(issue,
                    bookRepository.findById(issue.getIdBook()).get(),
                    readerRepository.findById(issue.getIdReader()).get()));
        }
        return reports;
    }

    /**
     * Gets reader by id
     * @param readerId
     * @return
     */
    public Reader getReaderById(long readerId) {
        Optional<Reader> optionalReader = readerRepository.findById(readerId);
        if (optionalReader.isEmpty()) {
            throw new NoSuchElementException("Нет читателя с id =" + readerId);
        }
        return optionalReader.get();
    }

    /**
     * Gets book list by readerID
     * @param readerId
     * @return
     */
    public List<Book> getBooksByReaderId(long readerId) {
        List<Book> books = new ArrayList<>();

        for (Issue issue: issueRepository.findAll()) {


            if (issue.getIdReader() == readerId && issue.getReturned_at() == null) {

                if (bookRepository.findById(issue.getIdBook()).isEmpty()) {
                    log.info("Не удалось найти книгу с id=" + issue.getIdBook());
                    throw new NoSuchElementException("Не удалось найти книгу с id=" + issue.getIdBook());
                }

                books.add(bookRepository.findById(issue.getIdBook()).get());

            }
        }
        return books;
    }
}
