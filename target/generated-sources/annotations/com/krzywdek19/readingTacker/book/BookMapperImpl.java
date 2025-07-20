package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T16:25:04+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        Integer pages = null;
        String description = null;

        id = book.getId();
        title = book.getTitle();
        pages = book.getPages();
        description = book.getDescription();

        AuthorDto authorDto = null;

        BookDto bookDto = new BookDto( id, title, authorDto, pages, description );

        return bookDto;
    }
}
