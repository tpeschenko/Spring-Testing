package ru.geekbrains.homework5.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.homework5.dto.BookDto;
import ru.geekbrains.homework5.model.Book;
import ru.geekbrains.homework5.repository.BookRepository;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void getAll() {
        List<Book> expectedBookList = List.of(
                new Book("Диалоги"),
                new Book("Опыты"),
                new Book("Так говорил Заратустра")
        );
        bookRepository.saveAll(expectedBookList);

        List<BookDto> actualBooks = bookService.getAll();

        Assertions.assertEquals(expectedBookList.size(), actualBooks.size());
        for (BookDto bookDto: actualBooks) {
            boolean found = expectedBookList.stream()
                    .filter(book -> Objects.equals(bookDto.getId(), book.getId()))
                    .allMatch(x -> Objects.equals(bookDto.getName(), x.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void getById() {
        Book expectedBook = bookRepository.save(new Book("Стоик"));

        BookDto actualBook = bookService.getById(expectedBook.getId());

        Assertions.assertNotNull(actualBook);
        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());

    }

    @Test
    void removeById() {
        Book expectedBook = bookRepository.save(new Book("Крестоносцы"));

        BookDto actualBook = bookService.removeById(expectedBook.getId());

        Assertions.assertNotNull(actualBook);
        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());

    }
}