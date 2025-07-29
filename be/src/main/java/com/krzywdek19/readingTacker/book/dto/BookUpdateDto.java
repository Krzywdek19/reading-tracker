package com.krzywdek19.readingTacker.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookUpdateDto {
    private String title;
    private Long authorId;
    private Integer pages;
    private Integer readPages;
    private String description;
}
