package ru.geekbrains.homework5.mappers;


import ru.geekbrains.homework5.dto.ReaderDto;
import ru.geekbrains.homework5.model.Reader;

import java.util.List;

public class ReaderMapper {

    public static Reader toReader(ReaderDto readerDto) {
        return new Reader(readerDto.getName());
    }

    public static ReaderDto toReaderDto(Reader reader) {
        return new ReaderDto(reader.getName());
    }

    public static List<ReaderDto> toListReaderDto(List<Reader> readers) {
        return readers.stream().map(ReaderMapper::toReaderDto).toList();
    }
}
