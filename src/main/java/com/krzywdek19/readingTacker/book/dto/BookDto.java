package com.krzywdek19.readingTacker.book.dto;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String title;
    private AuthorDto authorDto;
    private Long pages;
    private String description;
}
