package ru.geekbrains.homework5.services;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekbrains.homework5.dto.BookDto;
import ru.geekbrains.homework5.mappers.BookMapper;
import ru.geekbrains.homework5.model.Book;
import ru.geekbrains.homework5.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void getAll() {
        List<Book> expectedBooks = List.of(
                new Book("Сёгун"),
                new Book("Гайдзин"),
                new Book("Тай-пэн")
        );
        Mockito.when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<BookDto> actualBooks = bookService.getAll();

        Assertions.assertEquals(expectedBooks.size(), actualBooks.size());
    }

    @Test
    void getById() {
        Book expectedBook = new Book("Божественная комедия");
        Optional<Book> bookOptional = Optional.of(expectedBook);
        Mockito.when(bookRepository.findById(1L)).thenReturn(bookOptional);

        BookDto actualBook = bookService.getById(1);

        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());
    }

    @Test
    void getByIdThrowException() {

        Mockito.when(bookRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> bookService.getById(1));

    }


    @Test
    void removeById() {
        Book expectedBook = new Book("Тень Горы");
        Optional<Book> bookOptional = Optional.of(expectedBook);
        Mockito.when(bookRepository.findById(1L)).thenReturn(bookOptional);

        BookDto actualBook = bookService.removeById(1L);

        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());

    }

    @Test
    void createBook() {
        Book expectedBook = new Book("Шантарам");
        Mockito.when(bookRepository.save(expectedBook)).thenReturn(expectedBook);

        BookDto actualBook = bookService.createBook(BookMapper.toBookDto(expectedBook));

        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());
    }
}