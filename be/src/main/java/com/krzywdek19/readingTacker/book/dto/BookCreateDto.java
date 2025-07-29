package com.krzywdek19.readingTacker.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BookCreateDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Author cannot be undefined")
    private Long authorId;
    @NotNull(message = "Pages cannot be undefined")
    private Integer pages;
}
