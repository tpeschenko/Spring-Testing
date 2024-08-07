package ru.geekbrains.homework5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.homework5.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
