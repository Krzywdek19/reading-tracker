package com.krzywdek19.readingTacker.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateAuthorDto {
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String description;
}
