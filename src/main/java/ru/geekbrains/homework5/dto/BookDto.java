package ru.geekbrains.homework5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(name = "Книга")
@NoArgsConstructor
public class BookDto {

    private long id;

    @NotNull
    @Schema(name = "Название книги")
    private String name;

    public BookDto(String name) {
        this.name = name;
    }
}
