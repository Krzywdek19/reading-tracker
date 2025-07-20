package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.book.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookDto toBookDto(Book book);
}
