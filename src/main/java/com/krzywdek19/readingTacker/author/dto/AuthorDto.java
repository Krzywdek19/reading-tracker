package com.krzywdek19.readingTacker.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class AuthorDto {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String description;
}
