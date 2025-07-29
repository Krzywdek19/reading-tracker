package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.author.dto.CreateAuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(CreateAuthorDto authorDto);
    AuthorDto updateAuthor(Long authorId, AuthorDto authorDto);
    void deleteAuthor(Long authorId);
    AuthorDto getAuthorById(Long authorId);
    List<AuthorDto> getAllAuthors();
}
