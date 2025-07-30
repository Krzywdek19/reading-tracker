package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.author.dto.CreateAuthorDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(CreateAuthorDto authorDto);
    AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) throws AccessDeniedException;
    void deleteAuthor(Long authorId) throws AccessDeniedException;
    AuthorDto getAuthorById(Long authorId) throws AccessDeniedException;
    List<AuthorDto> getAllAuthors();
}
