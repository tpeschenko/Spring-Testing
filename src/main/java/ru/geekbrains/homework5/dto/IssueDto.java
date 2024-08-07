package ru.geekbrains.homework5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Запрос на выдачу")
public class IssueDto {
    @Schema(name = "id")
    private long id;

    @NotNull
    @Schema(name = "UID читателя")
    private long readerId;

    @NotNull
    @Schema(name = "UID Книги")
    private long bookId;

    public IssueDto(long readerId, long bookId, long id) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.id = id;
    }
}
