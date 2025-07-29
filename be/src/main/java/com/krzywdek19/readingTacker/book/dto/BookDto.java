package com.krzywdek19.readingTacker.book.dto;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String title;
    private AuthorDto authorDto;
    private Integer pages;
    private Integer readPages;
    private String description;
    private boolean isRead;
}
