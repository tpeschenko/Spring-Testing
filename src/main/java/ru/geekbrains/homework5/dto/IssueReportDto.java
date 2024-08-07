package ru.geekbrains.homework5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "Отчёт о выдаче книги читателю")
public class IssueReportDto {

    @Schema(name = "Название книги")
    private String bookName;

    @Schema(name = "Имя читателя")
    private String readerName;

    @Schema(name = "Время выдачи книги читателю")
    private LocalDateTime issued_at;

    @Schema(name = "Время возврата книги сервису")
    private  LocalDateTime returned_at;

    public IssueReportDto(String bookName, String readerName, LocalDateTime issued_at, LocalDateTime returned_at) {
        this.bookName = bookName;
        this.readerName = readerName;
        this.issued_at = issued_at;
        this.returned_at = returned_at;
    }
}
