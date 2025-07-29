package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto authorToAuthorDto(Author author);
}
