package ru.geekbrains.homework5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.homework5.model.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
