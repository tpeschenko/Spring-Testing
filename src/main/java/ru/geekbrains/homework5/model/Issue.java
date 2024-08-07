package ru.geekbrains.homework5.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "issues")
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idReader")
    private long idReader;

    @Column(name = "idBook")
    private long idBook;

    @Column(name = "issued_at")
    private  LocalDateTime issued_at;

    @Column(name = "returned_at", nullable = true)
    private  LocalDateTime returned_at;

    public Issue(long idReader, long idBook) {
        this.idReader = idReader;
        this.idBook = idBook;
        issued_at = LocalDateTime.now();
    }
}
