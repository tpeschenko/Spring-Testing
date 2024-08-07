package ru.geekbrains.homework5.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.homework5.dto.IssueDto;
import ru.geekbrains.homework5.dto.ReaderDto;
import ru.geekbrains.homework5.mappers.IssueMapper;
import ru.geekbrains.homework5.mappers.ReaderMapper;
import ru.geekbrains.homework5.model.Issue;
import ru.geekbrains.homework5.model.Reader;
import ru.geekbrains.homework5.repository.IssueRepository;
import ru.geekbrains.homework5.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReaderService {
    public static final String NOT_FOUND_READER_MESSAGE = "Не удалось найти читателя с id=";

    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void onCreatedDatabase() {
        readerRepository.saveAndFlush(new Reader("Андрей"));
        readerRepository.saveAndFlush(new Reader("Василий"));
        readerRepository.saveAndFlush(new Reader("Константин"));
        readerRepository.saveAndFlush(new Reader("Станислав"));
    }

    /**
     * Gets all readers
     * @return
     */
    public List<ReaderDto> getAll() {
        return ReaderMapper.toListReaderDto(readerRepository.findAll());
    }

    /**
     * Gets reader by id
     * @param id
     * @return
     */
    public ReaderDto getById(long id) {
        Reader reader = readerRepository.findById(id).orElse(null);
        if (reader != null) {
            return ReaderMapper.toReaderDto(reader);
        } else {
            log.info(NOT_FOUND_READER_MESSAGE +id);
            throw new NoSuchElementException(NOT_FOUND_READER_MESSAGE +id);
        }
    }

    /**
     * Removes reader by id
     * @param id
     */
    @Transactional
    public ReaderDto removeById(long id) {
        Reader reader = readerRepository.findById(id).orElse(null);
        if (reader == null) {
            log.info(NOT_FOUND_READER_MESSAGE + id);
            throw new NoSuchElementException(NOT_FOUND_READER_MESSAGE + id);
        }
        readerRepository.deleteById(id);
        return ReaderMapper.toReaderDto(reader);
    }

    /**
     * Adds reader to repository
     * @param
     * @return
     */
    @Transactional
    public ReaderDto createReader(ReaderDto readerRequest) {
        Reader reader = ReaderMapper.toReader(readerRequest);
        return ReaderMapper.toReaderDto(readerRepository.saveAndFlush(reader));
    }

    /**
     * Gets issue list by readerId
     * @param readerId
     * @return
     */
    public List<IssueDto> getIssues(long readerId) {
        if (readerRepository.findById(readerId).isEmpty()) {
            log.info(NOT_FOUND_READER_MESSAGE + readerId);
            throw new NoSuchElementException(NOT_FOUND_READER_MESSAGE + readerId);
        }

        List<Issue> issues = issueRepository.findAll()
                .stream()
                .filter(issue -> issue.getIdReader() == readerId && issue.getReturned_at() == null)
                .collect(Collectors.toList());

        if (issues.isEmpty()) {
            log.info("Не выдано книг читателю с id=" + readerId);
            throw new NoSuchElementException("Не выдано книг читателю с id=" + readerId);
        }
        return IssueMapper.toReaderDtoList(issues);
    }


}
