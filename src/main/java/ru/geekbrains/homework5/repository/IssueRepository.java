package ru.geekbrains.homework5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.homework5.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
