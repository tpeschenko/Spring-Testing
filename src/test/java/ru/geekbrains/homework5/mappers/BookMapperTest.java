package ru.geekbrains.homework5.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrains.homework5.dto.BookDto;
import ru.geekbrains.homework5.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    void toBook() {

        BookDto expectedBookDto = new BookDto("Война и Мир");

        Book actualBook = BookMapper.toBook(expectedBookDto);

        Assertions.assertEquals(expectedBookDto.getName(), actualBook.getName());
    }

    @Test
    void toBookDto() {

        Book expectedBook = new Book("Война миров");

        BookDto actualBookDto = BookMapper.toBookDto(expectedBook);

        Assertions.assertEquals(expectedBook.getName(), actualBookDto.getName());
        Assertions.assertEquals(expectedBook.getId(), actualBookDto.getId());

    }

    @Test
    void toListDto() {
        List<Book> expectedBooks = List.of(
                new Book("Сёгун"),
                new Book("Гайдзин"),
                new Book("Тай-пэн")
        );

        List<BookDto> actualBooks = BookMapper.toListDto(expectedBooks);

        Assertions.assertEquals(expectedBooks.size(), actualBooks.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            Assertions.assertEquals(expectedBooks.get(i).getId(), actualBooks.get(i).getId());
            Assertions.assertEquals(expectedBooks.get(i).getName(), actualBooks.get(i).getName());
        }
    }
}