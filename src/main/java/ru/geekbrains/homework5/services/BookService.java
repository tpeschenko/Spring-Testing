package ru.geekbrains.homework5.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.homework5.dto.BookDto;
import ru.geekbrains.homework5.mappers.BookMapper;
import ru.geekbrains.homework5.model.Book;
import ru.geekbrains.homework5.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    public static final String NOT_FOUND_BOOK_MESSAGE = "Не удалось найти книгу с id=";

    private final BookRepository bookRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void onCreatedDatabase() {
        bookRepository.save(new Book("Война и мир"));
        bookRepository.save(new Book("Мастер и Маргарита"));
        bookRepository.save(new Book("Приключения Буратино"));
        bookRepository.save(new Book("Декамерон"));
        bookRepository.save(new Book("Преступление и наказание"));
    }

    /**
     * Get all books
     * @return List Book instances
     */
    public List<BookDto> getAll() {
        Iterable<Book> iterable = bookRepository.findAll();
        List<Book> list =StreamSupport.stream(iterable.spliterator(), false).toList();
        return BookMapper.toListDto(list);
    }

    /**
     * Gets book by id
     * @param id long id
     * @return Book instance
     */
    public BookDto getById(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            log.info(NOT_FOUND_BOOK_MESSAGE +id);
            throw new NoSuchElementException(NOT_FOUND_BOOK_MESSAGE +id);
        } else {
            return BookMapper.toBookDto(book);
        }
    }

    /**
     * Removes book with id
     * @param id long id
     */
    @Transactional
    public BookDto removeById(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            log.info(NOT_FOUND_BOOK_MESSAGE + id);
            throw new NoSuchElementException(NOT_FOUND_BOOK_MESSAGE + id);
        }
        bookRepository.deleteById(id);
        return BookMapper.toBookDto(book);
    }

    /**
     * Adds book to repository
     * @param bookRequest BookRequest instance
     * @return Book instance
     */
    @Transactional
    public BookDto createBook(BookDto bookRequest) {
        Book book = BookMapper.toBook(bookRequest);
        return BookMapper.toBookDto(bookRepository.save(book));
    }
}
