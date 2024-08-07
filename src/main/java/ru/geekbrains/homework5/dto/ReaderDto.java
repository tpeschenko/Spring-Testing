package ru.geekbrains.homework5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Читатель")
public class ReaderDto {

    @NotNull
    @Schema(name = "Имя читателя")
    private String name;

    public ReaderDto(String name) {
        this.name = name;
    }
}
